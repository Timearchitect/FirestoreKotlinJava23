package se.gritacademy.firestorekotlin

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.firestore


class MainActivity : AppCompatActivity() {
    companion object{
         lateinit var tv:TextView
    }

    var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.text)
      write()
        read()
       // delete()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun write() {
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815,
        )

// Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun read() {
        var data:Any
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                var res:String=""
                for (document in result) {
                    data=document.data
                    Log.d(TAG, "${document.id} => ${document.data}")
                    res+=document.data.toString()+"\n"
                   tv.setText(res)
                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }.addOnCompleteListener {
                Log.w(TAG, "Done")

            }

    }
   fun update(){

       val city = hashMapOf(
           "name" to "Los Angeles",
           "state" to "CA",
           "country" to "USA",
       )

       db.collection("cities").document("LA")
           .set(city)
           .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
           .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
   }

    fun delete(){
        db.collection("users").document("bL7RlKftUBvDXs8ooz8Y")
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

    }
}