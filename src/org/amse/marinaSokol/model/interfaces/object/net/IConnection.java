package org.amse.marinaSokol.model.interfaces.object.net;

public interface IConnection {

    /**
     * ���������� ����, �� �������� ������� ����������.
     * @return  ����, �� �������� ������� ����������.
     * */
    IUsualLayer getSourceLayer();

    /**
     * ���������� ����, � ������� ������ ����������.
     * @return  ����, � ������� ������ ����������.
     * */
    ILayer getDestLayer();

    /**
     * ���������� ���� ����������
     * @return ���� ����������
     * */
    double[][] getWeights();

    /**
     * ������������� ���� ����������
     * @param weights - ����� ���� ����������
     * */
    void setWeights(double[][] weights);

    /**
     * ������������� ���� ����������
     * @param i
     * @param j - ����� ���� ����������
     * */
    //void setWeight(int i, int j, double  w)
    /**
     * ����������� ��� ��������� ���������� � ����� �� �����,
     * ����������� � ����������. ������� ����� ���� � ������������ � �����
     * ������������.��������� ���� ���������� �������.
     * */
    void createNewWeights();

    /**
     * ���������� �������� ����������
     * @return ��������
     * */
    int getDelay();

    /**
     * ������������� �������� ����������
     * @param delay - ��������
     * */
    void setDelay(int delay);
}
