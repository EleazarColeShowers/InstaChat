package com.example.instachatcompose.ui.activities.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instachatcompose.R
import com.example.instachatcompose.ui.activities.data.SecureStorage
import com.example.instachatcompose.ui.activities.mainpage.MessageActivity
import com.example.instachatcompose.ui.activities.signup.CustomCheckbox
import com.example.instachatcompose.ui.activities.signup.SignUpActivity
import com.example.instachatcompose.ui.theme.InstaChatComposeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


@Suppress("DEPRECATION")
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstaChatComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier= Modifier.fillMaxSize()) {
                        LoginPage(onBackPressed = {
                            onBackPressed()
                        })
                    }
                }
            }
        }

    }
}

@Composable
fun LoginPage(onBackPressed: () -> Unit){
    Column (
        modifier= Modifier.padding(horizontal = 15.dp)
    ){
        ReturnHome(onBackPressed ={
            onBackPressed()
        } )
        LoginForm()

    }
}

@Composable
fun ReturnHome(onBackPressed: () -> Unit){
    val returnArrow= painterResource(id = R.drawable.returnarrow)
    Spacer(modifier = Modifier.height(15.dp))
    Row(modifier= Modifier
        .fillMaxWidth()
    ) {

        Image(painter =returnArrow ,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onBackPressed()
                }
        )

    }
}

@Composable
fun LoginForm(){
    val context = LocalContext.current

    val auth = FirebaseAuth.getInstance()
    var isChecked by remember { mutableStateOf(false) }
    val (savedEmail, savedPassword) = SecureStorage.getUserCredentials(context)


//    var username by remember {
//        mutableStateOf("")
//    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val emailIcon= painterResource(id = R.drawable.emailicon)
    val passwordIcon= painterResource(id = R.drawable.passwordseen)

    Column{

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Welcome Back",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF050907),
                textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We're glad to have you back with us",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF696969),
            )
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Email",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF050907),
                )
            )
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0x33333333),
                        shape = RoundedCornerShape(size = 20.dp)
                    )
                    .height(48.dp)
                    .fillMaxWidth()
            ) {

                BasicTextField(
                    value = email,
                    onValueChange = { email = it },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 14.dp)
                )

                if (email.isEmpty()) {
                    Text(
                        text = "Enter your email address",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 24.dp,top=14.dp)
                    )
                }

                Image(
                    painter = emailIcon,
                    contentDescription = "Email",
                    modifier=Modifier.padding(start = 340.dp, top= 14.dp)

                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
//TODO: 4. Enable function to toggle password visibility
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Password",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF050907),
                )
            )
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0x33333333),
                        shape = RoundedCornerShape(size = 20.dp)
                    )
                    .height(48.dp)
                    .fillMaxWidth()
            ) {

                BasicTextField(
                    value = password,
                    onValueChange = { password = it },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 14.dp)
                )

                if (password.isEmpty()) {
                    Text(
                        text = "Enter your password",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 24.dp,top=14.dp)
                    )
                }

                Image(
                    painter = passwordIcon,
                    contentDescription = "Password",
                    modifier=Modifier.padding(start = 340.dp, top= 14.dp)

                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        //TODO: 1. enable the function to actually save passwords
        Row {
            CustomCheckbox(checked = isChecked) { checked ->
                isChecked = checked
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Save Password",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp

            )
        }
        Spacer(modifier = Modifier.height(44.5.dp))
        LoginBtn(
            isChecked = isChecked,
            onClick = {
                if (isChecked) {
                    // Save credentials using the utility class
                    SecureStorage.saveUserCredentials(context, email, password)
                } else{
                    Log.e(TAG,"did not save credentials")
                }

                performLogin(auth, context as ComponentActivity, email, password, onSuccess = {  username, profileImageUri ->
                    val intent = Intent(context, MessageActivity::class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("profileUri", profileImageUri)
                    context.startActivity(intent)
                },

//                    onFailure =)
            )
            },
            onUnchecked = { /* Handle unchecked state */ }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SignUpText()

    }
}

//TODO: On log in, only the username pops up and not the image due to the image being called differently in the message and profile setup page.
fun performLogin(
    auth: FirebaseAuth,
    context: ComponentActivity,
    email: String,
    password: String,
    onSuccess: (String, Uri?) -> Unit,  // Accept a callback with user ID and optional URI
) {
    if (email.isEmpty() || password.isEmpty()) {
        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
        return
    }

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(context) { task ->
            if (task.isSuccessful) {
                // Call the success callback before any further actions
                val user = auth.currentUser
                if (user != null) {
                    val userId = user.uid
//                    onSuccess(userId)
                    fetchUserProfile(  // Fetch user data upon successful login
                        userId,
                        onSuccess,
                    )
//                    fetchUserData(userId,onSuccess, onFailure = ) // Retrieve user data with userId
                }

                // You can perform additional actions after successful login if needed

                Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, "Error Occurred ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
}

fun fetchUserProfile(
    userId: String,
    onSuccess: (String, Uri?) -> Unit,  // Success callback with username and optional URI
//    onFailure: (Exception) -> Unit  // Failure callback for error handling
) {
    val database = FirebaseDatabase.getInstance().reference
    val userRef = database.child("users").child(userId)

    userRef.get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val snapshot = task.result
            if (snapshot.exists()) {
                val username = snapshot.child("username").getValue(String::class.java) ?: ""
                val profileImageUriString = snapshot.child("profileImageUri").getValue(String::class.java)
                val profileImageUri = profileImageUriString?.let { Uri.parse(it) }

                onSuccess(username, profileImageUri)  // Callback with data
            } else {
//                onFailure(Exception("User data not found"))  // Handle no data case
            }
        } else {
//            task.exception?.let { onFailure(it) }  // Handle retrieval failure
        }
    }
}

// TODO: 2. write function for user to stay logged in
@Composable
fun LoginBtn(
    isChecked: Boolean,
    onClick: () -> Unit,
    onUnchecked: () -> Unit
) {
    val context = LocalContext.current

    Surface(
        shape = RoundedCornerShape(25.dp), // Adjust the corner radius as needed
        color = Color(0xFF2F9ECE), // Change the background color as needed
        modifier = Modifier
            .height(54.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                if (isChecked) {
                    onClick()
                } else {
                    onUnchecked()
                    Toast
                        .makeText(context, "Tick the checkbox to proceed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                color = Color(0xFFFFFFFF), // Change the text color as needed
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Composable
fun SignUpText() {
    val context = LocalContext.current
    val signUpText = "SignUp "
    val text = buildAnnotatedString {
        append("Don't have an account?  ")
        pushStyle(
            style = SpanStyle(
                color = Color(android.graphics.Color.parseColor("#2F9ECE")), // Change color here
                textDecoration = TextDecoration.Underline
            )
        )
        append(signUpText)

    }

    ClickableText(text = text, onClick = {
        val startIndex = text.indexOf(signUpText)
        val endIndex = startIndex + signUpText.length
        if (it in startIndex..endIndex) {
            // Handle click action here if needed
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
            // For example, navigate to terms and conditions screen
        }
    },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 80.dp)
    )
}

