# Transformo

Transformo is a code generator software with the goal to read from several type of databases and generate usable and customizable code in your software as well as transform such database into a different format. Currently Transformo support Excel (xls and xlsx) as a database source, templates for C# code and JSON data generation.

## Compilation

The project now uses Maven, you can import the project in your favorite IDE and build from there.

## Database

Currently Transformo works with Excel sheets in format *xls* and *xlsx*. Each sheet in your excel file will represent a table. Each sheet must contain the following parameters:

![Database Format](/docs/database-example.png)

- First is the type of the field. Most of the primitive types are supported. (byte, sbyte, int, uint, short, ushort, long, ulong, float, double, char, bool, string, decimal)
- Second is the short version of the name of the field
- Third is the name of the field. add a **@** at the end if the field is the key of the table. Only one key is supported.

If the sheet name starts with a **!** the content will be ignored, this is useful in case you want to add comments, validations or others.

## Templates

The templates are files in plain text format that will be filled with the meta information from your table in order to represent a specific information use the following parameter:

### General Parameters:
- *$@date$* : This will represent the date of the generation as yyyy-MM-dd

### Per Table Parameters:
- *$table_name$* : This will represent the name of the table
- *$fields:file:FILE_NAME$* : This will read the file specified in FILE_NAME and fill it with each field information. This is needed when generating the template from a table. The *FILE_NAME* must be replaced with a different template file and you can add *[field_type]* inside to treat a field with a specific type accordingly.

### Per Field Parameters:
- *$field_name$* : This will represent the name of a field in the table
- *$field_short_name$* : This will represent the short name of a field in the table
- *$field_type$* : This will represent the type of the a field in the table

### Modifiers
You can modify the resulting text by adding *:* at the end of the parameter followed by one of this parameters:
- *capital_case* : This will modify the text keeping the first character upper case and all the rest in lower case
- *sentence_case* : This will modify the text keeping the first character of each word in upper case and the rest in lower case
- *lower_case* : This will modify the text all in lower case
- *upper_case* : This will modify the text all in upper case
- *camel_case* : This will remove the spaces between each word and keep the first character in upper case and the rest in lower case.
- *sneak_case* : This will replace the spaces between each word with an underscore (_) word and keep the first character in upper case and the rest in lower case.
- *class_case* : Same as camel case
- *underscore_space* : This will replace the spaces between each word.
- *clean* : This will keep the character until the end of an iteration. Useful when iterating fields and want to get rid of the last coma, like *$,:clean$*

### Example

For a general example of how to write a template, go to [Templates/CSharp-Models](Templates/CSharp-Models) and check the *template.txt* file and follow each file inside.

## Usage

### Generate Database

To generate a database use the following command

```sh
java -jar Transformo.jar -d my_database.xlsx -data -json
```

This will read the *my_database.xlsx* file and generate a JSON database. Replace *my_database.xlsx* for the path to your database file. You can use *-json* and *-sjson*, both will generate a JSON database but the second option will use the short name of the fields instead, this can reduce considerably the size of the output file. Also you can use the option *-tfile* to specify the path of the generated database like this:

```sh
java -jar Transformo.jar -d my_database.xlsx -data -sjson -tfile MyDatabase/database_short.json
```

### Generate Models from tables

To generate a representation of the tables in your database use the following command

```sh
java -jar Transformo.jar -d my_database.xlsx -tables -sfolder MyTemplates/ModelTemplates -tfolder GeneratedCode/Models -tfile '$table_name:class_case$.cs'
```

The will generate a representing classes of each table in the database based on your templates. The command will take all the meta information inside each table, read the template file inside *MyTemplates/ModelTemplates* (template.txt is the default index file of the templates), fill the template with the table information and generate files accordingly. You can also specify the name of the template file by adding *-sfile* and the name of the template file.

### Generate Fields from tables

To generate information of each unique field in the database use the following command

```sh
java -jar Transformo.jar -d my_database.xlsx -fields -sfolder MyTemplates/FieldTemplates -tfolder GeneratedCode/FieldInterfaces -tfile 'I$field_name:class_case$.cs'
```

This will work the same way as the other command to generate representing classes except this will represents unique fields. This is useful if you want to represent interfaces for better compatibility like Ids, names, descriptions. For example, assume that you have two models, one with ID, Name and Date and the other with Name, Description and Time. Both share the same field Name (assume the same type of data too), so you want to use the name of both to generate suggestions so you can pass the interface IName to you class instead of two different models. You can also generate an Interface that group several interfaces that can work in different places.

### Version
0.1.28b

### Copyright
Copyright (c) [Juan Silva](mailto:juanssl@gmail.com) All rights reserved
