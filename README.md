# flower-shop

This application implements The Flower Shop order bundling feature.

It's a *bit* overkill for the assignment, but I'm assuming The Flower Shop will need some other features.

The bundling process do NOT respect the order in which the order's lines are entered. This can be corrected if it's an issue.

I intentionally left out any logging capability, it seemed of little use currently and, in prospect, it will depend on the actual infrastructure.

## Build & Test

    mvn clean package

Use `shop.flower.AppTest` class to perform and e2e test of the application.

## Usage

To use the application, launch it from a shell with

    java -jar target/flower-shop-1.0-SNAPSHOT.jar

then enter each order line on a row and finish with an empty line.

    10 R12
    15 L09
    13 T58

The application will print the result on stdout.

    13 T58 $25.85
        2 x 5 $9.95
        1 x 3 $5.95
    10 R12 $12.99
        1 x 10 $12.99
    15 L09 $41.90
        1 x 9 $24.95
        1 x 6 $16.95



    
