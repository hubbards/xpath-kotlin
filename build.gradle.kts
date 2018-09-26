import org.gradle.jvm.tasks.Jar
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
  kotlin("jvm") version "1.2.71"
  id("org.jetbrains.dokka") version "0.9.16"
}

repositories {
  jcenter()
}

dependencies {
  implementation(kotlin("stdlib", "1.2.71"))
  testImplementation("junit:junit:4.12")
  testImplementation(kotlin("test", "1.2.71"))
}

val dokka by tasks.getting(DokkaTask::class) {
  outputFormat = "html"
  outputDirectory = "$buildDir/javadoc"
}

val dokkaJar by tasks.creating(Jar::class) {
  group = JavaBasePlugin.DOCUMENTATION_GROUP
  description = "Assembles Kotlin docs with Dokka"
  classifier = "javadoc"
  from(dokka)
}
