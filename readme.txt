# util_test

- использована java Oracle OpenJDK 22.0.1

- система сборки Maven 3.9.8

- библиотека Lombok 1.18.34
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.34</version>
        <scope>provided</scope>
    </dependency>

- Инструкция по запуску:
java -jar util.jar [options] <inFile 1> [inFile 2] ... [inFile N]
   Possible [options]:
       -s  display short statistics (default)
       -f  display full statistics
       -a  append result files (overwrite by default)
       -o  result files path (current directory by default)
       -p  result filenames prefix (none by default)
       -h  --help  this message

- возможно распознование целых чисел от –9 223 372 036 854 775 808 до 9 223 372 036 854 775 807, вещественных от ±4.9*10^-324 до ±1.7976931348623157*10^308, всё остальное попадает в тип "строки".