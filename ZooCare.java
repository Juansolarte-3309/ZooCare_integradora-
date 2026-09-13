/**
 * Analisis General del Problema:
 * La Fundacion BioHabitat administra un zoologico enfocado en la conservacion, educacion ambiental y cuidado de fauna silvestre.
 * Actualmente, la informacion operativa se maneja de forma manual, lo que genera duplicacion de datos, errores en raciones y perdida de informacion.
 * ZooCare es una aplicacion de consola desarrollada en Java que centraliza la operacion basica, valida reglas de negocio y realiza calculos operativos.
 * Para esta primera fase, la solucion se escribe en una unica clase con el metodo main y metodos auxiliares que separan entrada de datos,
 * validaciones, calculos y visualizacion de resultados. No se usan arreglos: cada entidad (zoologico, habitat, animal) se maneja mediante
 * variables individuales que se sobrescriben en cada nuevo registro, ya que esta fase es un prototipo de calculo y registro temporal.
 * Analisis de Requerimientos:
 *
 * REQ-01: Registrar Datos del Zoologico
 *   - Descripcion: permite ingresar la informacion general del zoologico (identificacion y presupuesto) y confirma en pantalla que quedo guardada.
 *   - Entradas: zooName (String), zooCity (String), zooAddress (String), zooLegalRepId (String), zooLegalRepName (String), zooMonthlyBudget (double)
 *   - Salidas: Mensaje de confirmacion en consola (String)
 *   - Ejemplo: zooName="Zoo Cali", zooMonthlyBudget=50000000.0 -> "Guardamos los datos del zoologico Zoo Cali con un presupuesto de $50,000,000.00 COP."
 *
 * REQ-02: Registrar Datos de un Habitat
 *   - Descripcion: permite ingresar el nombre, tipo de ambiente, area y presupuesto mensual de un habitat, y confirma su registro.
 *   - Entradas: habitatName (String), habitatEnvironmentType (int, 1 a 4), habitatArea (double), habitatMonthlyBudget (double)
 *   - Salidas: Mensaje de confirmacion en consola (String)
 *   - Ejemplo: habitatName="Sabana", habitatEnvironmentType=1, habitatArea=250.0 -> "Habitat 'Sabana' registrado con exito!"
 *
 * REQ-03: Registrar Datos de un Animal
 *   - Descripcion: permite ingresar los datos biologicos del animal necesarios para los calculos posteriores de racion y compatibilidad.
 *   - Entradas: animalName (String), animalSpecies (String), animalWeight (double), animalDietType (int), animalRequiredEnvironment (int), animalLifeStage (int), animalHealthStatus (int)
 *   - Salidas: Mensaje de confirmacion en consola (String)
 *   - Ejemplo: animalName="Simba", animalWeight=240.0, animalDietType=3 -> "Registramos a Simba. Llevamos 1 animal(es) ingresado(s)."
 *
 * REQ-04: Calcular Racion Diaria
 *   - Descripcion: a partir del peso, tipo de dieta, etapa de vida y estado de salud del animal registrado, calcula la racion base
 *     (porcentaje del peso segun la dieta) y le aplica unicamente el mayor de los ajustes aplicables (no son acumulables).
 *   - Entradas: animalWeight (double), animalDietType (int), animalLifeStage (int), animalHealthStatus (int)
 *   - Salidas: Racion diaria ajustada (double)
 *   - Ejemplo: Peso=240 kg, Omnivoro, Juvenil, En observacion -> base=8.40 kg, mayor ajuste=+20% (juvenil) -> Racion diaria ajustada = 10.08 kg
 *
 * REQ-05: Calcular Costo Mensual Estimado de Alimentacion
 *   - Descripcion: multiplica la racion diaria ajustada por el costo unitario del alimento segun la dieta y por 30 dias, y acumula
 *     el resultado en el total de costo de alimentacion de la sesion.
 *   - Entradas: Racion diaria ajustada (double) y costo unitario del alimento segun la dieta (double)
 *   - Salidas: Costo estimado a 30 dias (double)
 *   - Ejemplo: Racion=10.08 kg, Costo/kg=$15,200 -> Costo mensual = $4,596,480 COP
 *
 * REQ-06: Evaluar Compatibilidad Animal - Habitat
 *   - Descripcion: compara el ambiente requerido por el animal con el tipo de ambiente del habitat registrado. Si el habitat es de
 *     tipo Medico (futura Clinica Veterinaria) se considera siempre compatible; en cualquier otro caso, si no coinciden, se registra
 *     una alerta de incompatibilidad.
 *   - Entradas: animalRequiredEnvironment (int), habitatEnvironmentType (int)
 *   - Salidas: Resultado de compatibilidad (boolean) y alerta si no es compatible
 *   - Ejemplo: Requerido=Aviario, Habitat=Terrestre -> Alerta de incompatibilidad registrada.
 *
 * REQ-07: Calcular Espacio Requerido en Mapa
 *   - Descripcion: a partir del area del habitat, determina cuantas posiciones de 100 m2 necesitara en el futuro mapa del zoologico
 *     (maximo 4 posiciones, equivalentes a 400 m2, por habitat).
 *   - Entradas: habitatArea (double)
 *   - Salidas: Numero de posiciones en el mapa (int, de 1 a 4)
 *   - Ejemplo: Area=250 m2 -> Requiere 3 posiciones en el mapa.
 *
 * REQ-08: Resumen de la Sesion
 *   - Descripcion: antes de finalizar (o a solicitud del usuario), muestra cuantos animales se procesaron, cuantos habitats se
 *     evaluaron, cuantas alertas de incompatibilidad se generaron y el costo estimado acumulado de alimentacion de la sesion.
 *   - Entradas: Metricas acumuladas en variables de la clase (sin parametros de entrada del usuario)
 *   - Salidas: Resumen claro de la sesion en consola
 */
import java.util.Scanner;
public class ZooCare{
    private static Scanner sc = new Scanner(System.in);

    // Datos generales del zoologico
    private static String zooName = "";
    private static String zooCity = "";
    private static String zooAddress = "";
    private static String zooLegalRepId = "";
    private static String zooLegalRepName = "";
    private static double zooMonthlyBudget = 0.0;
    private static boolean isZooRegistered = false;

    // Datos del habitat actual
    private static String habitatName = "";
    private static int habitatEnvironmentType = 0;
    private static double habitatArea = 0.0;
    private static double habitatMonthlyBudget = 0.0;
    private static boolean isHabitatRegistered = false;

    // Datos del animal actual
    private static String animalName = "";
    private static String animalSpecies = "";
    private static double animalWeight = 0.0;
    private static int animalDietType = 0;
    private static int animalRequiredEnvironment = 0;
    private static int animalLifeStage = 0;
    private static int animalHealthStatus = 0;
    private static boolean isAnimalRegistered = false;

    // Metricas de la sesion
    private static int totalAnimalsProcessed = 0;
    private static int totalHabitatsEvaluated = 0;
    private static int totalIncompatibilityAlerts = 0;
    private static double totalCumulativeFeedingCost = 0.0;

    // Constantes de porcentaje de racion base
    private static final double HERBIVOROUS_FACTOR = 0.040;
    private static final double CARNIVOROUS_FACTOR = 0.030;
    private static final double OMNIVOROUS_FACTOR = 0.035;
    private static final double INSECTIVOROUS_FACTOR = 0.025;

    // Constantes de costo por kilogramo (COP)
    private static final double HERBIVOROUS_COST_PER_KG = 8800.0;
    private static final double CARNIVOROUS_COST_PER_KG = 25500.0;
    private static final double OMNIVOROUS_COST_PER_KG = 15200.0;
    private static final double INSECTIVOROUS_COST_PER_KG = 18300.0;

    // Constantes de porcentaje de incremento
    private static final double JUVENILE_BOOST = 0.20;
    private static final double QUARANTINE_RECOVERY_BOOST = 0.15;
    private static final double OBSERVATION_BOOST = 0.10;

    /**
     * Punto de entrada del programa. Muestra el menu principal en un ciclo y
     * delega cada opcion al metodo auxiliar correspondiente, hasta que el
     * usuario decide salir.
     *
     * @param args argumentos de linea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        boolean exitRequested = false;

        System.out.println("¡Hola! Te damos la bienvenida a ZooCare (Fundacion BioHabitat)");

        while (!exitRequested) {
            displayMenu();
            int option = readIntInRange("Elige una opcion (1-9): ", 1, 9);

            switch (option) {
                case 1:
                    registerZooData();
                    break;
                case 2:
                    registerHabitatData();
                    break;
                case 3:
                    registerAnimalData();
                    break;
                case 4:
                    processDailyRationCalculation();
                    break;
                case 5:
                    processMonthlyFeedingCostCalculation();
                    break;
                case 6:
                    processCompatibilityCheck();
                    break;
                case 7:
                    processSpaceCalculation();
                    break;
                case 8:
                    displaySessionSummary();
                    break;
                case 9:
                    exitRequested = true;
                    System.out.println("Cerrando sesion en ZooCare. Aqui tienes el resumen final:");
                    displaySessionSummary();
                    System.out.println("¡Hasta pronto! Que tengas un excelente dia.");
                    break;
                default:
                    System.out.println("Opcion no valida. Intenta de nuevo con un numero entre 1 y 9.");
                    break;
            }
        }
    }
    /**
     * Muestra por consola las opciones del menu principal. No recibe
     * parametros ni retorna ningun valor; solo produce salida.
     */
    private static void displayMenu() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Registrar datos generales del zoologico");
        System.out.println("2. Registrar datos de un habitat");
        System.out.println("3. Registrar datos de un animal");
        System.out.println("4. Calcular ración diaria del animal");
        System.out.println("5. Calcular costo mensual estimado de alimentacion");
        System.out.println("6. Verificar compatibilidad entre animal y habitat");
        System.out.println("7. Calcular espacio requerido en mapa por el habitat");
        System.out.println("8. Ver resumen operativo de la sesion");
        System.out.println("9. Salir");
    }
    /**
     * Solicita por consola los datos generales del zoologico (REQ-01), los
     * valida con los metodos de lectura auxiliares, los guarda en las
     * variables de la clase y muestra un mensaje de confirmacion. No retorna
     * ningun valor.
     */
    public static void registerZooData() {
        System.out.println("Registro del Zoologico");
        zooName = readNonEmptyString("¿Cual es el nombre del zoologico?: ");
        zooCity = readNonEmptyString("¿En que ciudad se encuentra?: ");
        zooAddress = readNonEmptyString("Direccion del lugar: ");
        zooLegalRepId = readNonEmptyString("Cedula o ID del representante legal: ");
        zooLegalRepName = readNonEmptyString("Nombre completo del representante legal: ");
        zooMonthlyBudget = readDoubleNonNegative("Presupuesto mensual asignado (COP): ");

        isZooRegistered = true;
        System.out.println("¡Listo! Guardamos los datos del zoologico " + zooName + " con un presupuesto de $" + zooMonthlyBudget + " COP.");
    }
    /**
     * Solicita por consola los datos de un habitat (REQ-02), los valida, los
     * guarda en las variables de la clase y actualiza el contador de
     * habitats evaluados de la sesion. No retorna ningun valor.
     */
    public static void registerHabitatData() {
        System.out.println("Registro de Hábitat");
        habitatName = readNonEmptyString("Nombre del habitat: ");

        System.out.println("Tipos de ambiente: 1) Terrestre  2) Acuatico  3) Aviario  4) Médico (Clínica Veterinaria)");
        habitatEnvironmentType = readIntInRange("Selecciona el tipo de ambiente (1-4): ", 1, 4);

        habitatArea = readDoublePositive("area del hábitat en m2 (debe ser mayor a 0): ");
        habitatMonthlyBudget = readDoubleNonNegative("Presupuesto mensual del hábitat (COP): ");

        isHabitatRegistered = true;
        totalHabitatsEvaluated++;

        System.out.println("¡Hábitat '" + habitatName + "' registrado con éxito! Llevamos " + totalHabitatsEvaluated + " hábitat(s) evaluado(s).");
    }
    /**
     * Solicita por consola los datos de un animal (REQ-03), los valida, los
     * guarda en las variables de la clase y actualiza el contador de
     * animales procesados de la sesion. No retorna ningun valor.
     */
    public static void registerAnimalData(){
        System.out.println("Registro de Animal");
        animalName = readNonEmptyString("Nombre del animal: ");
        animalSpecies = readNonEmptyString("Especie: ");
        animalWeight = readDoublePositive("Peso actual en kg (debe ser mayor a 0): ");

        System.out.println("Tipos de dieta: 1) Herbívora  2) Carnívora  3) Omnívora  4) Insectívora");
        animalDietType = readIntInRange("Selecciona el tipo de dieta (1-4): ", 1, 4);

        System.out.println("Ambiente que necesita: 1) Terrestre  2) Acuático  3) Aviario");
        animalRequiredEnvironment = readIntInRange("Selecciona el ambiente (1-3): ", 1, 3);

        System.out.println("Etapa de vida: 1) Juvenil  2) Adulto");
        animalLifeStage = readIntInRange("Selecciona la etapa de vida (1-2): ", 1, 2);

        System.out.println("Estado de salud: 1) Saludable  2) Cuarentena  3) Recuperación  4) En observación");
        animalHealthStatus = readIntInRange("Selecciona el estado de salud (1-4): ", 1, 4);

        isAnimalRegistered = true;
        totalAnimalsProcessed++;

        System.out.println("¡Perfecto! Registramos a " + animalName + " (" + animalSpecies + "). Llevamos " + totalAnimalsProcessed + " animal(es) ingresado(s).");
    }

    /**
     * Determina el ajuste (porcentaje) que corresponde a la etapa de vida de
     * un animal. Metodo de calculo puro, sin entrada ni salida por consola.
     *
     * @param lifeStageCode codigo de la etapa de vida (1 = Juvenil, 2 = Adulto)
     * @return JUVENILE_BOOST si la etapa es juvenil; de lo contrario, 0.0
     */
    private static double getLifeStageBoost(int lifeStageCode){
        double boost = 0.0;
        if (lifeStageCode == 1) {
            boost = JUVENILE_BOOST;
        }
        return boost;
    }
    /**
     * Determina el ajuste (porcentaje) que corresponde al estado de salud de
     * un animal. Metodo de calculo puro, sin entrada ni salida por consola.
     *
     * @param healthStatusCode codigo del estado de salud (1 = Saludable,
     *        2 = Cuarentena, 3 = Recuperación, 4 = En observación)
     * @return QUARANTINE_RECOVERY_BOOST si esta en cuarentena o recuperacion,
     *         OBSERVATION_BOOST si esta en observacion, o 0.0 en otro caso
     */
    private static double getHealthStatusBoost(int healthStatusCode) {
        double boost = 0.0;
        if (healthStatusCode == 2 || healthStatusCode == 3) {
            boost = QUARANTINE_RECOVERY_BOOST;
        } else if (healthStatusCode == 4) {
            boost = OBSERVATION_BOOST;
        }
        return boost;
    }
    /**
     * Calcula la racion diaria ajustada (REQ-04) del animal actualmente
     * registrado: racion base (peso por factor de dieta) multiplicada por
     * (1 + el mayor de los ajustes aplicables). Metodo de calculo puro: se
     * asume que ya hay un animal registrado y no realiza validaciones ni
     * imprime mensajes; quien lo invoque debe verificar antes que
     * isAnimalRegistered sea verdadero.
     *
     * @return la racion diaria ajustada, en kilogramos
     */
    public static double calculateDailyRation() {
        double basePercentage = getDietBasePercentage(animalDietType);
        double baseRation = animalWeight * basePercentage;

        double stageBoost = getLifeStageBoost(animalLifeStage);
        double healthBoost = getHealthStatusBoost(animalHealthStatus);

        double maxAdjustment = stageBoost;
        if (healthBoost > maxAdjustment) {
            maxAdjustment = healthBoost;
        }
        double adjustedRation = baseRation * (1.0 + maxAdjustment);
        return adjustedRation;
    }

    /**
     * Valida que haya un animal registrado, calcula su racion diaria
     * ajustada y muestra por consola el detalle del calculo (racion base,
     * cada ajuste candidato y el que finalmente se aplico). No retorna
     * ningun valor.
     */
    public static void processDailyRationCalculation() {
        if (!isAnimalRegistered) {
            System.out.println("No podemos calcular la ración porque no hay un animal registrado.");
            return;
        }

        double basePercentage = getDietBasePercentage(animalDietType);
        double baseRation = animalWeight * basePercentage;

        double stageBoost = getLifeStageBoost(animalLifeStage);
        double healthBoost = getHealthStatusBoost(animalHealthStatus);

        double maxAdjustment = stageBoost;
        if (healthBoost > maxAdjustment) {
            maxAdjustment = healthBoost;
        }

        double adjustedRation = calculateDailyRation();

        System.out.println("Cálculo de Ración Diaria");
        System.out.println("Animal: " + animalName + " (" + animalSpecies + ") | Peso: " + animalWeight + " kg");
        System.out.println("Dieta: " + getDietName(animalDietType) + " (Factor base: " + (basePercentage * 100) + "%)");
        System.out.println("Ración base: " + baseRation + " kg");
        System.out.println("Aumento por etapa: +" + (int)(stageBoost * 100) + "% | Aumento por salud: +" + (int)(healthBoost * 100) + "%");
        System.out.println("Aumento aplicado: +" + (int)(maxAdjustment * 100) + "% (tomando únicamente el mayor ajuste)");
        System.out.println("Ración diaria ajustada: " + adjustedRation + " kg/día");
    }

    /**
     * Calcula el costo mensual estimado de alimentacion (REQ-05) del animal
     * actualmente registrado: racion diaria ajustada por costo unitario del
     * alimento por 30 dias. Metodo de calculo puro: se asume que ya hay un
     * animal registrado y no realiza validaciones ni imprime mensajes; quien
     * lo invoque debe verificar antes que isAnimalRegistered sea verdadero.
     *
     * @return el costo mensual estimado de alimentacion, en COP
     */
    public static double calculateMonthlyFeedingCost() {
        double adjustedDailyRation = calculateDailyRation();
        double unitCostPerKg = getDietUnitCost(animalDietType);

        double dailyCost = adjustedDailyRation * unitCostPerKg;
        double monthlyCost = dailyCost * 30.0;
        return monthlyCost;
    }

    /**
     * Valida que haya un animal registrado, calcula su costo mensual
     * estimado de alimentacion, lo suma al total acumulado de la sesion y
     * muestra por consola el detalle del calculo. No retorna ningun valor.
     */
    public static void processMonthlyFeedingCostCalculation() {
        if (!isAnimalRegistered) {
            System.out.println("Primero debes registrar un animal para calcular su costo de alimentación.");
            return;
        }

        double adjustedDailyRation = calculateDailyRation();
        double unitCostPerKg = getDietUnitCost(animalDietType);
        double dailyCost = adjustedDailyRation * unitCostPerKg;
        double monthlyCost = calculateMonthlyFeedingCost();

        totalCumulativeFeedingCost += monthlyCost;

        System.out.println("Costo Mensual Estimado de Alimentación");
        System.out.println("Animal: " + animalName + " | Dieta: " + getDietName(animalDietType));
        System.out.println("Ración diaria: " + adjustedDailyRation + " kg");
        System.out.println("Costo del alimento: $" + unitCostPerKg + " COP por kg");
        System.out.println("Costo por día: $" + dailyCost + " COP");
        System.out.println("Costo estimado a 30 días: $" + monthlyCost + " COP");
        System.out.println("(Se han sumado $" + monthlyCost + " COP al total acumulado de la sesión)");
    }

    /**
     * Determina si el animal y el habitat actualmente registrados son
     * compatibles en ambiente (REQ-06). Un habitat de tipo Medico (4) -la
     * futura Clinica Veterinaria por defecto- se considera siempre
     * compatible, ya que esta exento de esta regla. Metodo de calculo puro,
     * sin entrada ni salida por consola.
     *
     * @return true si el habitat es de tipo Medico o si el ambiente
     *         requerido por el animal coincide con el del habitat;
     *         false en caso contrario
     */
    public static boolean checkCompatibility() {
        if (!isAnimalRegistered || !isHabitatRegistered) {
            return false;
        }

        if (habitatEnvironmentType == 4) {
            return true;
        }

        return (animalRequiredEnvironment == habitatEnvironmentType);
    }
    /**
     * Valida que haya un animal y un habitat registrados, evalua su
     * compatibilidad y muestra el resultado por consola. Si son
     * incompatibles, aumenta el contador de alertas de incompatibilidad de
     * la sesion e informa la causa. No retorna ningun valor.
     */
    public static void processCompatibilityCheck() {
        if (!isAnimalRegistered || !isHabitatRegistered) {
            System.out.println("Para revisar compatibilidad necesitas tener registrados tanto un animal como un hábitat.");
            if (!isAnimalRegistered) System.out.println(" -> Falta registrar el animal (Opción 3)");
            if (!isHabitatRegistered) System.out.println(" -> Falta registrar el hábitat (Opción 2)");
            return;
        }
        System.out.println("Evaluación de Compatibilidad");
        System.out.println("Animal: " + animalName + " (Requiere: " + getEnvironmentName(animalRequiredEnvironment) + ")");
        System.out.println("Hábitat: " + habitatName + " (Ambiente: " + getEnvironmentName(habitatEnvironmentType) + ")");

        boolean isCompatible = checkCompatibility();

        if (isCompatible) {
            System.out.println("¡Todo bien! El animal y el hábitat son totalmente compatibles.");
        } else {
            totalIncompatibilityAlerts++;
            System.out.println("¡ALERTA DE INCOMPATIBILIDAD! (Alerta #" + totalIncompatibilityAlerts + ")");
            System.out.println("CAUSA: El animal requiere el ambiente '" + getEnvironmentName(animalRequiredEnvironment) + "', pero el hábitat '" + habitatName + "' es de tipo '" + getEnvironmentName(habitatEnvironmentType) + "'.");
            System.out.println("Esta alerta se ha sumado al registro de la sesión.");
        }
    }

    /**
     * Calcula cuantas posiciones de 100 m2 del futuro mapa necesita un
     * habitat (REQ-07), segun su area. Toda fraccion de 100 m2 iniciada
     * requiere una posicion adicional, hasta el maximo de negocio de 4
     * posiciones (400 m2) por habitat. Metodo de calculo puro, sin entrada
     * ni salida por consola.
     *
     * @param area el area del habitat, en metros cuadrados
     * @return 0 si el area no es positiva; en caso contrario, un valor
     *         entre 1 y 4
     */
    public static int calculateRequiredSpace(double area) {
        if (area <= 0) {
            return 0;
        }
        if (area <= 100.0) {
            return 1;
        } else if (area <= 200.0) {
            return 2;
        } else if (area <= 300.0) {
            return 3;
        } else {
            return 4;
        }
    }
    /**
     * Valida que haya un habitat registrado, calcula las posiciones de mapa
     * que requiere y muestra el resultado por consola, incluyendo una nota
     * cuando el area supera el maximo de 400 m2 permitido por habitat. No
     * retorna ningun valor.
     */
public static void processSpaceCalculation() {
    if (!isHabitatRegistered) {
        System.out.println("Primero registra un hábitat en la opción 2 para poder calcular su espacio.");
        return;
    }
    int requiredPositions = calculateRequiredSpace(habitatArea);
    System.out.println("[Espacio Requerido en Mapa]");
    System.out.println("Hábitat: " + habitatName + " rea: " + habitatArea + " m2");
    System.out.println("Este hábitat requerirá " + requiredPositions + " posición(es) contigua(s) de 100 m2 en el mapa.");
    if (habitatArea > 400.0) {
        System.out.println("Nota: Como el área supera los 400 m2, se le asigna el máximo posible de 4 posiciones.");
    }
}
    /**
     * Muestra por consola el resumen operativo de la sesion (REQ-08): datos
     * del zoologico si fue registrado, animales procesados, habitats
     * evaluados, alertas de incompatibilidad generadas y el costo estimado
     * acumulado de alimentacion. No retorna ningun valor.
     */
    public static void displaySessionSummary() {
        System.out.println("Resumen Operativo de la Sesión");

        String displayedZooName = "Sin registrar";
        String displayedZooCity = "N/A";
        String displayedZooLegalRepName = "N/A";
        if (isZooRegistered) {
            displayedZooName = zooName;
            displayedZooCity = zooCity;
            displayedZooLegalRepName = zooLegalRepName;
        }
        System.out.println("Zoológico: " + displayedZooName);
        System.out.println("Ciudad: " + displayedZooCity);
        System.out.println("Representante legal: " + displayedZooLegalRepName);
        System.out.println("Presupuesto mensual: $" + zooMonthlyBudget + " COP");
        System.out.println("Animales procesados: " + totalAnimalsProcessed);
        System.out.println("Hábitats evaluados: " + totalHabitatsEvaluated);
        System.out.println("Alertas por incompatibilidad: " + totalIncompatibilityAlerts);
        System.out.println("Costo acumulado estimado de alimentación: $" + totalCumulativeFeedingCost + " COP");
    }
    /**
     * Traduce el codigo de tipo de dieta a su nombre para mostrar en
     * pantalla.
     *
     * @param dietCode codigo del tipo de dieta (1 = Herbivora, 2 = Carnivora,
     *        3 = Omnivora, 4 = Insectivora)
     * @return el nombre de la dieta, o "Desconocida" si el codigo no es valido
     */
    private static String getDietName(int dietCode) {
        switch (dietCode) {
            case 1: return "Herbívora";
            case 2: return "Carnívora";
            case 3: return "Omnívora";
            case 4: return "Insectívora";
            default: return "Desconocida";
        }
    }
    /**
     * Obtiene el factor de racion base (porcentaje del peso corporal) segun
     * el tipo de dieta.
     *
     * @param dietCode codigo del tipo de dieta (1 = Herbivora, 2 = Carnivora,
     *        3 = Omnivora, 4 = Insectivora)
     * @return el factor de racion base como fraccion decimal (por ejemplo,
     *         0.04 equivale a 4%), o 0.0 si el codigo no es valido
     */
    private static double getDietBasePercentage(int dietCode) {
        switch (dietCode) {
            case 1: return HERBIVOROUS_FACTOR;
            case 2: return CARNIVOROUS_FACTOR;
            case 3: return OMNIVOROUS_FACTOR;
            case 4: return INSECTIVOROUS_FACTOR;
            default: return 0.0;
        }
    }

    /**
     * Obtiene el costo unitario del alimento por kilogramo segun el tipo de
     * dieta.
     *
     * @param dietCode codigo del tipo de dieta (1 = Herbivora, 2 = Carnivora,
     *        3 = Omnivora, 4 = Insectivora)
     * @return el costo unitario en COP por kilogramo, o 0.0 si el codigo no
     *         es valido
     */
    private static double getDietUnitCost(int dietCode) {
        switch (dietCode) {
            case 1: return HERBIVOROUS_COST_PER_KG;
            case 2: return CARNIVOROUS_COST_PER_KG;
            case 3: return OMNIVOROUS_COST_PER_KG;
            case 4: return INSECTIVOROUS_COST_PER_KG;
            default: return 0.0;
        }
    }

    /**
     * Traduce el codigo de tipo de ambiente a su nombre para mostrar en
     * pantalla.
     *
     * @param envCode codigo del tipo de ambiente (1 = Terrestre,
     *        2 = Acuatico, 3 = Aviario, 4 = Medico)
     * @return el nombre del ambiente, o "Desconocido" si el codigo no es valido
     */
    private static String getEnvironmentName(int envCode) {
        switch (envCode) {
            case 1: return "Terrestre";
            case 2: return "Acuático";
            case 3: return "Aviario";
            case 4: return "Médico (Clínica Veterinaria)";
            default: return "Desconocido";
        }
    }

    /**
     * Solicita repetidamente un dato por consola hasta recibir una cadena de
     * texto no vacia.
     *
     * @param prompt el mensaje que se muestra antes de leer el dato
     * @return la cadena de texto ingresada, sin espacios al inicio o al final
     */
    private static String readNonEmptyString(String prompt) {
        String input = "";
        while (input.trim().isEmpty()) {
            System.out.print(prompt);
            input = sc.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Este campo no puede quedar vacío. Por favor escribe un valor válido.");
            }
        }
        return input.trim();
    }

    /**
     * Solicita repetidamente un dato por consola hasta recibir un numero
     * entero valido dentro de un rango cerrado.
     *
     * @param prompt el mensaje que se muestra antes de leer el dato
     * @param min valor minimo aceptado (incluido)
     * @param max valor maximo aceptado (incluido)
     * @return el numero entero validado ingresado por el usuario
     */
    private static int readIntInRange(String prompt, int min, int max) {
        int value = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                value = sc.nextInt();
                sc.nextLine();
                if (value >= min && value <= max) {
                    valid = true;
                } else {
                    System.out.println("Esa opción no está en la lista. Ingresa un número entre " + min + " y " + max + ".");
                }
            } else {
                System.out.println("Necesitamos un número entero. Intenta de nuevo.");
                sc.nextLine();
            }
        }
        return value;
    }

    /**
     * Solicita repetidamente un dato por consola hasta recibir un numero
     * decimal estrictamente mayor que cero. Se usa para datos que las reglas
     * de negocio exigen mayores a cero (peso, area, etc.).
     *
     * @param prompt el mensaje que se muestra antes de leer el dato
     * @return el numero decimal positivo validado ingresado por el usuario
     */
    private static double readDoublePositive(String prompt) {
        double value = 0.0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                value = sc.nextDouble();
                sc.nextLine();
                if (value > 0.0) {
                    valid = true;
                } else {
                    System.out.println("El número debe ser mayor a 0. Intenta de nuevo.");
                }
            } else {
                System.out.println("Por favor ingresa un número válido.");
                sc.nextLine();
            }
        }
        return value;
    }

    /**
     * Solicita repetidamente un dato por consola hasta recibir un numero
     * decimal mayor o igual a cero. Se usa para datos que las reglas de
     * negocio exigen no negativos (presupuestos, costos, etc.).
     *
     * @param prompt el mensaje que se muestra antes de leer el dato
     * @return el numero decimal no negativo validado ingresado por el usuario
     */
    private static double readDoubleNonNegative(String prompt) {
        double value = 0.0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                value = sc.nextDouble();
                sc.nextLine();
                if (value >= 0.0) {
                    valid = true;
                } else {
                    System.out.println("El número no puede ser negativo. Intenta de nuevo.");
                }
            } else {
                System.out.println("Por favor ingresa un número válido.");
                sc.nextLine();
            }
        }
        return value;
    }
}