import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cupid.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddEmail : AppCompatActivity() {

    private lateinit var emailAddressEditText: EditText
    private lateinit var nextButton: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_email)

        emailAddressEditText = findViewById(R.id.emailAddress)
        nextButton = findViewById(R.id.next)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        nextButton.setOnClickListener {
            saveEmailToFirestore()
        }
    }

    private fun saveEmailToFirestore() {
        val emailAddress = emailAddressEditText.text.toString().trim()

        if (TextUtils.isEmpty(emailAddress)) {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = mAuth.currentUser?.uid
        if (userId != null) {
            val user = hashMapOf(
                "emailAddress" to emailAddress
            )

            db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Email saved successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, AddPassword::class.java))
                    finish() // Finish current activity to prevent going back
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error saving email: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}
