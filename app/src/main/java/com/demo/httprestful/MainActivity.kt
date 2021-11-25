package com.demo.httprestful

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
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
    lateinit var avatar_url: String;

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
    fun placeProfileImage(url: String): ImageRequest {

        return ImageRequest(url,
            { bitmap ->
                profilePicture.setImageBitmap(bitmap);
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
            {
                Toast.makeText(this@MainActivity, "Failed to get image..", Toast.LENGTH_SHORT).show()
            })
    }
    fun searchGithubUser(username: String): JsonObjectRequest {
        val url = "https://api.github.com/users/${username}"

        return JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                avatar_url = response.getString("avatar_url")
                queue.add(placeProfileImage(avatar_url))

                realName.text = response.getString("name")
                location.text = "Lokasyon: ${response.getString("location")}"
                company.text = "Şirket: ${response.getString("company")}"
                followers.text = "Takipçi: ${response.getString("followers")}"
            },
            {
                Toast.makeText(this@MainActivity, "Failed to get data..", Toast.LENGTH_SHORT).show()
            })

    }
    val listener = View.OnClickListener { view ->
        queue.add(searchGithubUser(username.text.toString()))
    }
}
