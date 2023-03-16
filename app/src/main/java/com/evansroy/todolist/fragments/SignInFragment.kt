package com.evansroy.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.evansroy.todolist.R
import com.evansroy.todolist.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    // Declare the necessary variables
    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Call the init() function to initialize navController and mAuth
        init(view)

        // Set up a click listener for the sign up button
        binding.textViewSignUp.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        // Set up a click listener for the sign in button
        binding.nextBtn.setOnClickListener {
            // Get the email and password input from the user
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString()

            // If the email and password fields are not empty, call the loginUser() function
            if (email.isNotEmpty() && pass.isNotEmpty())
                loginUser(email, pass)
            // Otherwise, display a message that empty fields are not allowed
            else
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to sign in the user with Firebase Authentication
    private fun loginUser(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful)
            // Navigate to the home fragment if sign-in is successful
                navController.navigate(R.id.action_signInFragment_to_homeFragment)
            else
            // Display an error message if sign-in is unsuccessful
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()

        }
    }

    // Function to initialize navController and mAuth
    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance()
    }

}
