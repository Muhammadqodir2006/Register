package uz.itschool.register



import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUp : AppCompatActivity() {

    private var usersList = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        usersList = getUsers()




        create_account.setOnClickListener{
            val myUsername = username.text.toString().trim()
            val myEmail = email.text.toString().trim()
            val myPassword = password.text.toString().trim()
            val usernames = ArrayList<String>()
            val emails = ArrayList<String>()
            for (i in usersList){
                usernames.add(i.username)
                emails.add(i.email)
            }
            if (usernames.contains(myUsername)){
                Toast.makeText(applicationContext, "Username already exists", Toast.LENGTH_LONG).show()
                username.text?.clear()
                return@setOnClickListener
            }
            if (!myEmail.contains('@')){
                Toast.makeText(applicationContext, "Email address must contain '@' sign", Toast.LENGTH_LONG).show()
                email.text?.clear()
                return@setOnClickListener
            }
            if (emails.contains(myEmail)){
                Toast.makeText(applicationContext, "This email is already registered", Toast.LENGTH_LONG).show()
                email.text?.clear()
                return@setOnClickListener
            }
            if (myPassword.length < 8){
                Toast.makeText(applicationContext, "Password must contain al least 8 characters", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            signUp(User(myUsername, myEmail, myPassword))
        }
    }

    private fun signUp(user: User){
        val gson = Gson()
        val sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        usersList.add(user)
        val list:String = gson.toJson(usersList)
        edit.putString("users", list).apply()
        Toast.makeText(applicationContext, "Successfully signed up", Toast.LENGTH_SHORT).show()
        finish()
        val a  = Intent(applicationContext, Interface::class.java)
        a.putExtra("username", user.username)
        startActivity(a)
    }
    private fun getUsers(): ArrayList<User> {
        val gson = Gson()
        val sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        val data: String = sharedPreferences.getString("users", "")!!
        val typeToken = object  : TypeToken<ArrayList<User>>(){}.type
        if (data.isEmpty()) return ArrayList<User>()
        return gson.fromJson(data, typeToken)
    }
}