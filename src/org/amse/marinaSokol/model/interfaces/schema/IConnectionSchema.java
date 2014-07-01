package org.amse.marinaSokol.model.interfaces.schema;

/**
 * Makes a description of an arrow
 * @author Sokol Marina
 * */
public interface IConnectionSchema extends IShapeSchema {

    /**
     * ���������� ����, �� �������� ������� �������.
     * @return  ����, �� �������� ������� �������.
     * */
    IUsualLayerSchema getSourceLayerSchema();

    /**
     * ���������� ����, � ������� ������ �������.
     * @return ����, � ������� ������ �������.
     * */
    ILayerSchema getDestLayerSchema();

    /**
     * ������������� ����, � ������� ������ �������.
     * @param layerSchema  - ���� � ������� ����� ������� �������.
     * */
    void setDestLayerSchema(ILayerSchema layerSchema);

    /**
     *@return ���������� true, ���� ��� ������� ������ � false �����.
     * */
    boolean isDirect();

    /**
     * ���������� ��������� ����� ������ � �����������.
     * @param x  - ���������� �����
     * @param y  - ���������� �����
     * @return �������� ����� (x, y) � �����
     * */
    int proximity(int x, int y);
}
