package org.amse.marinaSokol.model.interfaces.object.net;

import java.util.List;


public interface IUsualLayer {
    /**
     * ���������� ���������� �������� � ����
     * @return ���������� ��������
     * */
    int getNeuronsNumber();

    /**
     * ������������� ���������� �������� � ����.
     * ��� �� ������������ �� ���� �����������,
     * � ������ ����������� ���� ���� � ������������� ����� ����������� �����.
     * ���� �������� ������ ���������� �������.
     * @param number - ���������� ��������
     * */
    void setNeuronsNumber(int number);   //���������� �������� � ����


    /**
     * ��������� ����������, ��������� �� ������ ����.
     * @param newOutput - ����� ����������
     * */
    void addOutputConnection(IConnection newOutput);

    /**
     * ���������� ������ ���� ��������� �� ����� ���� ����������
     * @return  ������ ���� ��������� �� ����� ���� ����������
     * */
    List<? extends IConnection> getOutputConnections();

}
