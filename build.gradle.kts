plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.16.0"
}

group = "com.gaussdb"
version = "1.0.0"

repositories {
    mavenCentral()
}

intellij {
    version.set("2023.2")
    type.set("IC")
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

patchPluginXml {
    sinceBuild.set("232")
    untilBuild.set("241.*")
    changeNotes.set("""
        <dl>
            <dt><b>1.0.0</b></dt>
            <dd>Initial release for MyBatis GaussDB Log Plugin</dd>
            <dd>Support GaussDB SQL log parsing</dd>
            <dd>Restore mybatis sql log to original whole executable sql</dd>
        </dl>
    """.trimIndent())
}