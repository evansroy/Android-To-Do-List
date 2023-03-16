package com.evansroy.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.evansroy.todolist.R
import com.evansroy.todolist.databinding.FragmentSignUpBinding

import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    // declare variables for navigation, Firebase authentication, and view binding
    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using view binding
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize the Firebase authentication and navigation controller
        init(view)

        // set up a click listener for the sign in text view, to navigate to the sign in screen
        binding.textViewSignIn.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        // set up a click listener for the "next" button, to register the user with Firebase authentication
        binding.nextBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString()
            val verifyPass = binding.verifyPassEt.text.toString()

            // validate that all fields are filled out
            if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty()) {
                // validate that the password and verify password fields match
                if (pass == verifyPass) {
                    // if validation passes, register the user with Firebase authentication
                    registerUser(email, pass)
                } else {
                    // if the passwords don't match, show an error message
                    Toast.makeText(context, "Password is not same", Toast.LENGTH_SHORT).show()
                }
            } else {
                // if any field is empty, show an error message
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // function to register the user with Firebase authentication
    private fun registerUser(email: String, pass: String) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                // if registration is successful, navigate to the home screen
                navController.navigate(R.id.action_signUpFragment_to_homeFragment)
            } else {
                // if registration fails, show an error message
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // function to initialize the Firebase authentication and navigation controller
    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance()
    }
}
