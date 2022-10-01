package finalproject.raytracing.math;

import finalproject.raytracing.math.Ray;
import finalproject.raytracing.math.Vector3;

public class Line {
    public Vector3 pointA;
    public Vector3 pointB;

    public  Line(Vector3 pointA, Vector3 pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public Ray asRay() {
        return new Ray(pointA, pointB.subtract(pointA).normalize());
    }
}
