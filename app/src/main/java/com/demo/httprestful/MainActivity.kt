package com.demo.httprestful

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var searchButton: Button
    lateinit var username: EditText
    lateinit var profilePicture: ImageView

    lateinit var realName: TextView
    lateinit var location: TextView
    lateinit var company: TextView
    lateinit var followers: TextView

    lateinit var queue: RequestQueue;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton = findViewById<Button>(R.id.search_button)
        username = findViewById<EditText>(R.id.username)
        profilePicture = findViewById<ImageView>(R.id.profile_picture)
        realName = findViewById<TextView>(R.id.realname)
        location = findViewById<TextView>(R.id.location)
        company = findViewById<TextView>(R.id.company)
        followers = findViewById<TextView>(R.id.followers)

        searchButton.setOnClickListener(listener)

        queue = Volley.newRequestQueue(this)


    }
    fun searchGithubUser(username: String): JsonObjectRequest {
        val url = "https://api.github.com/users/${username}"

        return JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                realName.text = response.getString("name")
                location.text = "Lokasyon: ${response.getString("location")}"
                company.text = "Şirket: ${response.getString("company")}"
                followers.text = "Takipçi: ${response.getString("followers")}"
            },
            {
                Toast.makeText(this@MainActivity, "Fail to get data..", Toast.LENGTH_SHORT).show()
            })

    }
    val listener = View.OnClickListener { view ->
        queue.add(searchGithubUser(username.text.toString()))
    }
}
