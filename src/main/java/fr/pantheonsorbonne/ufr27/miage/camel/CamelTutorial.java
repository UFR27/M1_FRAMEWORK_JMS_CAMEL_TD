package fr.pantheonsorbonne.ufr27.miage.camel;

import fr.pantheonsorbonne.ufr27.miage.camel.model.Perimeter;
import fr.pantheonsorbonne.ufr27.miage.camel.model.Triangle;
import fr.pantheonsorbonne.ufr27.miage.camel.processor.EquilateralProcessor;
import fr.pantheonsorbonne.ufr27.miage.camel.processor.EquilateralTrianglePerimeterComputerProcessor;
import fr.pantheonsorbonne.ufr27.miage.camel.processor.RandomTrianglePeremeterComputerProcessor;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CamelTutorial extends RouteBuilder {

    @Override
    public void configure() {


        from("file:data/inbox")
                .unmarshal().csv()
                .process(new EquilateralProcessor())
                .marshal().jacksonXml(Triangle.class)
                .choice()
                .when(header("TriangleType").isEqualTo("equilateral"))
                .to("jms:queue/miage.nherbaut.triangles.equilateral")
                .when(header("TriangleType").isEqualTo("autre"))
                .to("jms:queue/miage.nherbaut.triangles.autres");


        from("jms:queue/miage.nherbaut.triangles.equilateral")
                .unmarshal().jacksonXml(Triangle.class)
                .log("writing (equil) ${body}")
                .process(new EquilateralTrianglePerimeterComputerProcessor())
                .to("direct://perimeter-writer");

        from("jms:queue/miage.nherbaut.triangles.autres")
                .unmarshal().jacksonXml(Triangle.class)
                .log("writing (autre) ${body}")
                .process(new RandomTrianglePeremeterComputerProcessor())
                .to("direct://perimeter-writer");
        from("direct://perimeter-writer")
                .marshal().json(Perimeter.class)
                .log("writing perimeter ${body}")
                .toD("file:data/perimeter?filename=${id}.xml");


    }

}
