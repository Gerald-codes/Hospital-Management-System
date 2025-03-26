plugins {
    kotlin("jvm") version "1.9.22"
    id("java")
    id("application")
}

group = "org.groupJ"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.code.gson:gson:2.12.1")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
}

application { // Configure the main class for the application plugin
    mainClass.set("org.groupJ.Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.groupJ.Main" // Only needed for executable JAR
    }
    // This line is necessary to include all dependencies into the jar file
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    archiveFileName.set("out.jar") // Optional: Specify the JAR file name
    destinationDirectory.set(layout.buildDirectory.dir("../executable/"))
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile> {
    options.release.set(21)
}

// Create the runJar task
tasks.register<JavaExec>("runJar") {
    dependsOn(tasks.jar) // build the jar
    classpath = files(tasks.jar.get().outputs.files) // set classpath
    mainClass.set("org.groupJ.Main") // Specify the main class

    // Optional: Set JVM arguments if needed
    // jvmArgs = listOf("-Xmx256m")
    // jvmArgs = listOf("--add-opens", "java.base/java.time=ALL-UNNAMED")
    // Optional: Set standard input if you need to pass input to the process
    standardInput = System.`in`
}