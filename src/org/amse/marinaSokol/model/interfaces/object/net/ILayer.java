package org.amse.marinaSokol.model.interfaces.object.net;

import java.util.List;

public interface ILayer extends IUsualLayer {

    /**
     * ��������� ����������, �������� � ��� ����.
     * @param newInput - ����� ����������
     * */
    void addInputConnection(IConnection newInput);

    /**
     * ���������� ������ ���� �������� � ���� ���� ����������
     * @return  ������ ���� �������� � ���� ���� ����������
     * */
    List<? extends IConnection> getInputConnections();

    /**
     * ���������� ������� ��������� ����
     * @return ������� ���������
     * */
    IActivationFunctor getActivation();

    /**
     * ������������� ������� ��������� ����
     * @param type - ������� ���������
     * */
    void setActivation(ActivationFunctorType type);

    /**
     * ������� ����� �� �������� ����� ������� ���������
     * @param neuronSum - ������� ����� �� ��������
     * @return ����� �� ��������
     * */
    double[] getActivationResult(double[] neuronSum);
}

