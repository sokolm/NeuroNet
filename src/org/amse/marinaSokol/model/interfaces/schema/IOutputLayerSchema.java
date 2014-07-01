package org.amse.marinaSokol.model.interfaces.schema;

public interface IOutputLayerSchema extends ILayerSchema {

    /**
     * ������������� ��� �����, � ������� ����� �������� ������
     * @param fileName - ��� �����
     * */
    void setFileNameOutput(String fileName);

    /**
     * ���������� ��� �����, � ������� ����� �������� ������
     * @return fileName - ��� �����
     * */
    String getFileNameOutput();

    /**
     * ������������� ��� �����, �� �������� ����� ������� ��������� ������
     * @param fileName - ��� �����
     * */
    void setFileNameRightOutput(String fileName);

    /**
     * ���������� ��� �����, �� �������� ����� ������� ��������� ������
     * @return fileName - ��� �����
     * */
    String getFileNameRightOutput();
}
