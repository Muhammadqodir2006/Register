package uz.itschool.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    private var usersList = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        usersList = getUsers()

        signIn.setOnClickListener {
            usersList = getUsers()
            val myUsername = username1.text.toString().trim()
            val myPassword = password1.text.toString().trim()
            val usernames = ArrayList<String>()
            val emails = ArrayList<String>()
            for (i in usersList) {
                usernames.add(i.username)
                emails.add(i.email)
            }
            var id:Int = -1
            for (i in usernames.indices){
                val a = usernames[i]
                if (a == myUsername) {
                    id = i
                }
            }
            for (i in emails.indices){
                val a = emails[i]
                if (a == myUsername) {
                    id = i
                }
            }
            if (id == -1){
                Toast.makeText(applicationContext, "Incorrect password", Toast.LENGTH_LONG).show()
                password1.text?.clear()
                return@setOnClickListener
            }
            val a  = Intent(applicationContext, Interface::class.java)
            a.putExtra("username", myUsername)
            startActivity(a)

        }

        signUp.setOnClickListener{
            val a = Intent(applicationContext, SignUp::class.java)
            startActivity(a)
        }

    }

    private fun getUsers(): ArrayList<User> {
        val sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        val gson = Gson()
        val data: String = sharedPreferences.getString("users", "")!!
        val typeToken = object : TypeToken<ArrayList<User>>() {}.type
        if (data.isEmpty()) return ArrayList<User>()
        return gson.fromJson(data, typeToken)

    }
}