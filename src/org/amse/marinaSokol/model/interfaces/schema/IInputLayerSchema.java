package org.amse.marinaSokol.model.interfaces.schema;

public interface IInputLayerSchema extends IUsualLayerSchema {
    /**
     * ������������� ��� �����, �� �������� ����� ����������� �����
     * @param fileName - ��� �����
     * */
    void setFileName(String fileName);

    /**
     * ���������� ��� �����, �� �������� ����� ����������� �����
     * @return fileName - ��� �����
     * */
    String getFileName();

}
