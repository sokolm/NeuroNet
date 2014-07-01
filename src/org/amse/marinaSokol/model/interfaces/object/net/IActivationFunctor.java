package org.amse.marinaSokol.model.interfaces.object.net;

public interface IActivationFunctor {

    /**
     * ������� ���������
     * @param x - �����, �������� �� ������� ���������
     * @return ����� �������
     * */
    double getFunction(double x);

    /**
     * ����������� ������� ���������
     * @param x - �����, �������� �� �������
     * @return �����
     * */
    double getDerivation(double x);

    /**
     * ��� ������� ���������
     * @return ��� ������� ���������
     * */
    String getNameFunction();
}
