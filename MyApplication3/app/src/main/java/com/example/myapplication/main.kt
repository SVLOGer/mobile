// Определение sealed-интерфейса для всех транспортных средств
sealed interface Vehicle

// Синглтон Самокат
object Scooter : Vehicle

// Велосипедная рама из различных сплавов
enum class BicycleFrame {
    ALUMINUM, STEEL, CARBON
}

// Велосипедное колесо с диаметром
data class BicycleWheel(val diameter: Int)

// Класс Велосипед
data class Bicycle(
    val brand: String,
    val frontWheel: BicycleWheel,
    val rearWheel: BicycleWheel,
    val frame: BicycleFrame
) : Vehicle

// Виды топлива
enum class FuelType {
    DIESEL, GASOLINE_92, GASOLINE_95, GASOLINE_98, GASOLINE_100
}

// ДВС (двигатель внутреннего сгорания)
data class InternalCombustionEngine(val volume: Double, val fuelType: FuelType)

// Виды дисков
enum class WheelDisk {
    CAST, FORGED, STAMPED
}

// Колесо автомобиля с диаметром, фирмой и диском
data class CarWheel(val diameter: Int, val brand: String, val disk: WheelDisk)

// Руль для автомобиля
data class SteeringWheel(val type: String)

// Виды автопилотов
enum class AutopilotSystem {
    YANDEX, TESLA
}

// Класс для автомобиля
open class Car(
    open val wheels: List<CarWheel>,
) : Vehicle

// Класс для автомобиля с ДВС, наследующийся от Car
data class CombustionCar(
    val engine: InternalCombustionEngine,
    val steeringWheel: SteeringWheel,
    override val wheels: List<CarWheel>
) : Car(wheels)

// Электрический двигатель
data class ElectricMotor(val power: Int)

// Класс для электрического автомобиля, наследующийся от Car
data class ElectricCar(
    val motor: ElectricMotor,
    val autopilot: AutopilotSystem,
    override val wheels: List<CarWheel>
) : Car(wheels)
