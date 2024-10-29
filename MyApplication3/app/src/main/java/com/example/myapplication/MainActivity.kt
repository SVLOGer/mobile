package com.example.myapplication

import Bicycle
import BicycleWheel
import CarWheel
import CombustionCar
import ElectricCar
import ElectricMotor
import InternalCombustionEngine
import SteeringWheel
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vehicleInfo = findViewById<TextView>(R.id.vehicleInfo)

        // Самокат
        findViewById<Button>(R.id.scooterButton).setOnClickListener {
            vehicleInfo.text = "Это самокат."
        }

        // Велосипед
        findViewById<Button>(R.id.bicycleButton).setOnClickListener {
            val bicycle = Bicycle(
                brand = "Trek",
                frontWheel = BicycleWheel(26),
                rearWheel = BicycleWheel(26),
                frame = BicycleFrame.CARBON
            )
            vehicleInfo.text = "Велосипед марки ${bicycle.brand} с рамой из ${bicycle.frame}. Колеса диаметром ${bicycle.frontWheel.diameter} дюймов."
        }

        // Автомобиль с ДВС
        findViewById<Button>(R.id.combustionCarButton).setOnClickListener {
            val combustionCar = CombustionCar(
                engine = InternalCombustionEngine(2.0, FuelType.GASOLINE_95),
                wheels = List(4) { CarWheel(17, "Michelin", WheelDisk.CAST) },
                steeringWheel = SteeringWheel("Спортивный")
            )
            vehicleInfo.text = "Автомобиль с ДВС объемом ${combustionCar.engine.volume} л на ${combustionCar.engine.fuelType}. Колеса: ${combustionCar.wheels[0].brand}."
        }

        // Электромобиль
        findViewById<Button>(R.id.electricCarButton).setOnClickListener {
            val electricCar = ElectricCar(
                motor = ElectricMotor(300),
                wheels = List(4) { CarWheel(18, "Pirelli", WheelDisk.FORGED) },
                autopilot = AutopilotSystem.TESLA
            )
            vehicleInfo.text = "Электромобиль с электромотором на ${electricCar.motor.power} л.с. с автопилотом от ${electricCar.autopilot}."
        }
    }
}
