plugins {
  kotlin("jvm") version "1.2.70"
}

repositories {
  jcenter()
}

dependencies {
  implementation(kotlin("stdlib", "1.2.70"))
  testImplementation("junit:junit:4.12")
  testImplementation(kotlin("test", "1.2.70"))
}
