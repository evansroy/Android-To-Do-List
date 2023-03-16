package com.evansroy.todolist.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.evansroy.todolist.R
import com.google.firebase.auth.FirebaseAuth

// Define the SplashFragment class
class SplashFragment : Fragment() {

    // Declare some private properties for the class
    private lateinit var mAuth: FirebaseAuth
    private lateinit var navController: NavController

    // Inflate the layout for the fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    // Initialize the view and define a handler to execute some code after a delay
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Call the init method to initialize some properties
        init(view)

        // Determine if the user is logged in or not
        val isLogin: Boolean = mAuth.currentUser != null

        // Define a handler to execute some code after a delay
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({

            // If the user is logged in, navigate to the home screen
            if (isLogin)
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            // If the user is not logged in, navigate to the login screen
            else
                navController.navigate(R.id.action_splashFragment_to_signInFragment)

        }, 2000) // Delay the execution of the code by 2 seconds
    }

    // Initialize some properties for the class
    private fun init(view: View) {
        mAuth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(view)
    }
}
