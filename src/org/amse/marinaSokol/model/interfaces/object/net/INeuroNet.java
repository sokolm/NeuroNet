package org.amse.marinaSokol.model.interfaces.object.net;

import org.amse.marinaSokol.model.interfaces.object.INeuroNetVisitor;

import java.util.List;

public interface INeuroNet {
    /**
     * ���������� ���� ����
     * @return ����
     * */
    List<? extends IUsualLayer> getLayers();

    /**
     * ����� ��������� �������� �� ���� � ������ �������
     * @param visitor - ����������, ������� ����� ��� �������� ����
     * */
    void walkDirect(INeuroNetVisitor visitor);

    /**
     * ����� ��������� �������� �� ���� � �������� �������
     * @param visitor - ����������, ������� ����� ��� �������� ����
     * */
    void walkBack(INeuroNetVisitor visitor);
}
