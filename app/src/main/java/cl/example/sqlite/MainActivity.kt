package cl.example.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import dbTables.DbHandle
import dbTables.Person
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    //region Métodos CRUD
    fun select(view: View)
    {
        val listPersons = DbHandle(this).selectPersona();
        var string = "";

        for (p in listPersons)
        {
            string += "${p.id} - ${p.nombre} ${p.apellido} ${p.rut} \n";

        }

        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    fun insert(view: View)
    {
        val person = createPerson() ?: return;
        DbHandle(this).insertPersona(person);
        resetInputs();
    }

    fun update(view: View)
    {
        val person = createPerson(true) ?: return;
        DbHandle(this).updatePersona(person);
        resetInputs();
    }

    fun delete(view: View)
    {
        val id = getNumberInput(inputId) ?: return;
        DbHandle(this).deletePersona(id);
        resetInputs();
    }
    //endregion CRUD

    private fun createPerson(getId: Boolean = false): Person? {
        val name = getTextInput(inputNombre);
        val lastName = getTextInput(inputApellido);
        val rut = getTextInput(inputRut);
        val pass = getTextInput(inputContraseña);
        val id = if (getId) getNumberInput(inputId) else 0;

        name ?: return null;
        lastName ?: return null;
        rut ?: return null;
        pass ?: return null;
        id ?: return null;

        return Person(id, name, lastName, rut, pass);
    }

    //region Métodos privados
    private fun getTextInput(input: TextInputEditText): String?
    {
        val text = input.text.toString();

        if (text == "")
        {
            input.error = "¡Campo obligatorio!"
            return null;
        }

        return text;
    }

    private fun getNumberInput(input: TextInputEditText): Int?
    {
        val number: Int;

        try {
            number = Integer.parseInt(input.text.toString());
        }
        catch (e: NumberFormatException)
        {
            input.error = "¡Ingrese un número!"
            return null;
        }

        return number;
    }

    private fun resetInputs()
    {
        inputNombre.setText("");
        inputNombre.error = null;

        inputApellido.setText("");
        inputApellido.error = null;

        inputRut.setText("");
        inputRut.error = null;

        inputContraseña.setText("");
        inputContraseña.error = null;

        inputId.setText("");
        inputId.error = null;
    }
    //endregion

}