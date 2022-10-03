package fr.pantheonsorbonne.ufr27.miage.camel.processor;

import fr.pantheonsorbonne.ufr27.miage.camel.model.Point;
import fr.pantheonsorbonne.ufr27.miage.camel.model.Triangle;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

public class EquilateralProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        List<List<String>> points = (List<List<String>>) exchange.getMessage().getBody();
        var triangle = new Triangle();
        Point initialPoint = null;
        for (List<String> point : points) {
            triangle.addPoint(Triangle.toPoint(point));

        }

        var d1 = Triangle.distance(triangle.getPoint()[0], triangle.getPoint()[1]);
        var d2 = Triangle.distance(triangle.getPoint()[1], triangle.getPoint()[2]);
        var d3 = Triangle.distance(triangle.getPoint()[2], triangle.getPoint()[0]);
        if (Triangle.equals(d1, d2) && Triangle.equals(d3, d2) && Triangle.equals(d1, d3)) {
            exchange.getMessage().setHeader("TriangleType", "equilateral");
        } else {
            exchange.getMessage().setHeader("TriangleType", "autre");
        }

        exchange.getMessage().setBody(triangle);


    }
}
