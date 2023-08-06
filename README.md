# Introduction 

This is a basic program to test the working of Jinterface with our transit time model predictions.

## Directory Structure

The directory contains a `maven` project as the server and an `erlang` program as client. 
The communication happens via server-client interaction.

## Analysis 

The program (as is) is configured to run `200,000` runs out of which the first `10,000` runs are excluded to calculate the average time of processing.

The average time came out to be `63.84 ms`.

## Prerequisites

You will need a complete installation of `Erlang` along with `Jinterface`. 
If you don't have one, please do so by following the [Erlang repository](https://github.com/erlang/otp).

You can find the Jinterface `jar` location by using the following command.

```bash
erl
1> code:priv_dir(jinterface).
"/usr/local/lib/erlang/lib/jinterface-1.13.2/priv"
```

Install the `jar` file so that you can access this via `maven`.

```bash
mvn install:install-file \
   -Dfile=/usr/local/lib/erlang/lib/jinterface-1.13.2/priv/OtpErlang.jar \
   -DgroupId=com.ericsson.otp \
   -DartifactId=erlang \
   -Dversion=1.13.2 \
   -Dpackaging=jar \
   -DgeneratePom=true
```

```xml
<dependency>
    <groupId>com.ericsson.otp</groupId>
    <artifactId>erlang</artifactId>
    <version>1.13.2</version>
</dependency>
```

## Run the program 

You will need to run both the `maven` project and the `erlang` module.

First, run the Java server

```bash
cd maven_project/myproject
mvn clean install
mvn exec:java -Dexec.mainClass="com.example.HelloWorld"
```

Then run the Erlang program

```bash
cd erlang
erl -sname client
1> c(client).
2> client:start().
```