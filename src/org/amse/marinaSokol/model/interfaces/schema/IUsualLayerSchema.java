package org.amse.marinaSokol.model.interfaces.schema;

import java.util.List;

public interface IUsualLayerSchema extends IShapeSchema {

    /**
     * @return ����������(��������) ������ �������� ���� ����
     */
    int getX();

    /**
     * @return ����������(��������) ������ �������� ���� ����
     */
    int getY();

    /**
     * @return ������ ����
     */
    int getWidth();

    /**
     * @return ������ ����
     */
    int getHeight();

    /**
     * ���������� ���� � �����  (x, y)
     * @param x - ���������� �� ��� x
     * @param y - ���������� �� ��� y
     */
    void moveTo(int x, int y);

    /**
     * ������������� ����� ������� ����
     * @param width  - �������� ������ ����
     * @param height - �������� ������ ����
     */
    void resize(int width, int height);

    /**
     * @return ���������� true ���� ����� (�, �) ���������� � ���� � false �����
     * @param x - ���������� ����� �� �� ��� �
     * @param y - ���������� ����� �� ��� y
     */
    boolean containsPoint(int x, int y);

    /**
     * @return ���������� ������ �������, ��������� �� ����
     */
    List<? extends IConnectionSchema> getOutputConnectionsSchema();

    /**
     * ������������� ����� ���� ����� �������������� ����������
     * @param id - ����� ����
     * */
    void setId(int id);

    /**
     * ���������� ����� ���� ����� �������������� ����������
     * @return id - ����� ����
     * */
    int getId();

}
