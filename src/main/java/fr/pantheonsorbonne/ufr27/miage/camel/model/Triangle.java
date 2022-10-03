package fr.pantheonsorbonne.ufr27.miage.camel.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.List;

@XmlRootElement
public class Triangle {

    public static boolean equals(double d1, double d2) {
        return Math.abs(d1 - d2) < 0.000000001;
    }

    public static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void addPoint(Point p) {
        for (int i = 0; i < this.point.length; i++) {
            if ((this.point[i]) == null) {
                this.point[i] = p;
                return;
            }

        }
        throw new RuntimeException("all vertices set");
    }

    public static Point toPoint(List<String> coordinates) {
        double x = Double.valueOf(coordinates.get(0));
        double y = Double.valueOf(coordinates.get(1));
        return new Point(x, y);
    }

    public Point[] getPoint() {
        return point;
    }

    public void setPoint(Point[] point) {
        this.point = point;
    }

    Point[] point = new Point[3];

    @Override
    public String toString() {
        return "Triangle{" +
                "point=" + Arrays.toString(point) +
                '}';
    }
}
