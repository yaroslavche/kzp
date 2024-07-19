package io.yaroslavche.kzp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import io.yaroslavche.kzp.data.AppDatabase
import io.yaroslavche.kzp.data.Uas
import io.yaroslavche.kzp.ui.theme.KZPTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "kzp"
        ).build()

        CoroutineScope(Dispatchers.IO).launch {
            db.uasDao().insert(Uas(name = "Name"))
        }

        setContent {
            KZPTheme {
                DisplayUasRecords(db = db)
            }
        }
    }
}

@Composable
fun DisplayUasRecords(db: AppDatabase) {
    val uasList by produceState<List<Uas>>(emptyList()) {
        db.uasDao().getAll().observeForever { value = it }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (uasList.isEmpty()) {
                Text(
                    text = "No data available",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                uasList.forEach { uas ->
                    Text(
                        text = "#${uas.id} ${uas.name}",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KZPTheme {
        val db = Room.inMemoryDatabaseBuilder(
            LocalContext.current,
            AppDatabase::class.java
        ).build()
        DisplayUasRecords(db = db)
    }
}
