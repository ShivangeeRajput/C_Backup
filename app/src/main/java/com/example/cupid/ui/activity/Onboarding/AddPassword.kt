import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cupid.R
import com.example.cupid.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddPassword : AppCompatActivity() {

    private lateinit var passwordEditText: EditText
    private lateinit var agreeAndJoinButton: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private var firstName: String = ""
    private var lastName: String = ""
    private var emailAddress: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_password)

        // Initialize Firebase instances
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Retrieve data from previous activities
        firstName = intent.getStringExtra("firstName") ?: ""
        lastName = intent.getStringExtra("lastName") ?: ""
        emailAddress = intent.getStringExtra("emailAddress") ?: ""

        // Initialize views
        passwordEditText = findViewById(R.id.password)
        agreeAndJoinButton = findViewById(R.id.agreeAndJoin)
        progressBar = findViewById(R.id.registerProgressBar)

        // Button click listener
        agreeAndJoinButton.setOnClickListener {
            val password = passwordEditText.text.toString().trim()

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Register user with email and password
            registerUserWithEmailAndPassword(emailAddress, password)
        }
    }

    private fun registerUserWithEmailAndPassword(email: String, password: String) {
        progressBar.visibility = ProgressBar.VISIBLE

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    if (user != null) {
                        // Save additional user details to Firestore
                        saveUserDetailsToFirestore(user.uid)
                    } else {
                        Toast.makeText(
                            this,
                            "Authentication failed. Please try again later.",
                            Toast.LENGTH_SHORT
                        ).show()
                        progressBar.visibility = ProgressBar.GONE
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this,
                        "Authentication failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.visibility = ProgressBar.GONE
                }
            }
    }

    private fun saveUserDetailsToFirestore(userId: String) {
        val user = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "emailAddress" to emailAddress
            // Add more fields as needed
        )

        db.collection("users").document(userId)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish() // Finish current activity to prevent going back
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving user details: ${e.message}", Toast.LENGTH_SHORT).show()
                progressBar.visibility = ProgressBar.GONE
            }
    }
}
