package org.amse.marinaSokol.model.interfaces.schema;

public interface IOutputLayerSchema extends ILayerSchema {

    /**
     * Устанавливает имя файла, в который будут записаны выходы
     * @param fileName - имя файла
     * */
    void setFileNameOutput(String fileName);

    /**
     * Возвращает имя файла, в который будут записаны выходы
     * @return fileName - имя файла
     * */
    String getFileNameOutput();

    /**
     * Устанавливает имя файла, из которого будут считаны требуемые выходы
     * @param fileName - имя файла
     * */
    void setFileNameRightOutput(String fileName);

    /**
     * Возвращает имя файла, из которого будут считаны требуемые выходы
     * @return fileName - имя файла
     * */
    String getFileNameRightOutput();
}
