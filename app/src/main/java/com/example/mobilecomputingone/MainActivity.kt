package com.example.mobilecomputingone

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MainActivity : AppCompatActivity() {
    var editname: EditText? = null
    var editphone: EditText? = null
    var editaddress: EditText? = null
    var editFinalData: TextView? = null
    var saveBtn : Button? = null
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val TAG : String = "TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editname = findViewById(R.id.nameEdit)
        editphone = findViewById(R.id.phoneNumberEdit)
        editaddress = findViewById(R.id.addressEdit)
        saveBtn = findViewById(R.id.saveBtn)

        editFinalData = findViewById(R.id.resultData)
        readData()
       saveBtn?.setOnClickListener{
           saveToFirebase()
       }
    }
    fun saveToFirebase() {
        val name = editname!!.text.toString()
        val phone = editphone!!.text.toString()
        val address = editaddress!!.text.toString()
        val product: MutableMap<String, Any> = HashMap()
        product["Name"] = name
        product["Phone"] = phone
        product["Address"] = address
        db.collection("contacts")
            .add(product)
            .addOnSuccessListener { Log.d(TAG, "Successfully Stored") }
            .addOnFailureListener { Log.d(TAG, "Wrong ! in Store") }
    }
    fun readData() {
        db.collection("contacts")
            .get()
            .addOnCompleteListener { task ->
                val result = StringBuffer()
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d(TAG, document.id + " ( " + document.data+" )")
                        result.append("person Name  : ").append(document.data["Name"]).append("\n\n")
                            .append("person Phone : ").append(document.data["Phone"]).append("\n\n")
                            .append("person Address : ").append(document.data["Address"])
                            .append("\n--------------------------------------------------\n")
                    }
                    editFinalData!!.text = result
                } else {
                    Log.w(TAG, "Error  document.", task.exception)
                }
            }
    }


}