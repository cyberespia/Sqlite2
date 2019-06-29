package com.mihost.sqlite2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_usuarios.*

class AgregarUsuarios : AppCompatActivity() {

    //lateinit var handler: DBaseHandler



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_usuarios)




        agregarUsuarioBtn.setOnClickListener {

            if (usuarioEdit.text.isEmpty()){
                Toast.makeText(this, "Ingrese Usuario", Toast.LENGTH_LONG).show()
                usuarioEdit.requestFocus()
            } else {

                val usuario = Usuarios()

                usuario.nombreUsuario = usuarioEdit.text.toString()
                usuario.passwordUsuario = passwordEdit.text.toString()
                usuario.nivelUsuario = nivelEdit.text.toString().toInt()

                //MainActivity.handler.agregarUsuarios(this, usuarios)

                MainActivity.handler.agregarUsuarios(this, usuario)

                limpiarCampos()
                usuarioEdit.requestFocus()
            }


        }


        cerraBt.setOnClickListener {


            limpiarCampos()
            finish()

        }

    }


    private fun limpiarCampos() {

        usuarioEdit.text.clear()
        passwordEdit.text.clear()
        nivelEdit.text.clear()
    }

}
