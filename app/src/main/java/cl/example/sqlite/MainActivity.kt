package cl.example.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import dbTables.DbHandle
import dbTables.Person

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var handle: DbHandle = DbHandle(this);

        var state = handle.insertPersona(Person(
            0,
            "Gonzalo",
            "Zapata",
            "123455678-9",
            "contraseña"
        ));

        if (state < 0)
        {
            Toast.makeText(this,"ERROR!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"AGREGADO!", Toast.LENGTH_LONG).show();
        }


    }

    fun doUpdate(view: View)
    {
        var listPersonas: List<Person> = DbHandle(this).selectPersona();
        var string: String = "";

        for (p in listPersonas)
        {
            string += "${p.nombre} ${p.apellido} ${p.rut} \n";
        }

        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }
}