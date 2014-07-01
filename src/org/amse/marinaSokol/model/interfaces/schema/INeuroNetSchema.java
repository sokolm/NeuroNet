package org.amse.marinaSokol.model.interfaces.schema;
import org.amse.marinaSokol.model.interfaces.object.net.INeuroNet;

import java.util.List;

/**
  * Makes a description of a neuroNet
  * @author Sokol Marina
  */
public interface INeuroNetSchema {

    /**
     * ����� ��������� ���� � ����
     * @param x - ���������(��������) �������� ������ ����
     * @param y - ����������(��������) �������� ������ ����
     * @param width - ������ ����
     * @param height - ������ ����
     * @return ����������� ����
     */
    ILayerSchema addLayerSchema(int x, int y, int width, int height);

    /**
     * ����� ��������� ������� ���� � ����
     * @param x - ���������(��������) �������� ������ ����
     * @param y - ����������(��������) �������� ������ ����
     * @param width - ������ �������� ����
     * @param height - ������ �������� ����
     * @return ����������� ������� ����
     */
    IInputLayerSchema addInputLayerSchema(int x, int y, int width, int height);
    
    /**
     * ����� ��������� �������� ���� � ����
     * @param x - ���������(��������) �������� ������ ����
     * @param y - ����������(��������) �������� ������ ����
     * @param width - ������ ��������� ����
     * @param height - ������ ��������� ����
     * @return ����������� �������� ����
     */
    IOutputLayerSchema addOutputLayerSchema(int x, int y, int width, int height);

    /**
     * ����� ������� ���� �� ����
     * @param layerSchema - ��������� ����
     */
    void removeLayerSchema(IUsualLayerSchema layerSchema);

    /**
     * ���������� ����, �������� ����������� ����� (x, y) �
     * ���������� ��� � ����� ������.
     * ���� ����� ����� ���������, ���������� ��������� � ������.
     * ���� ����� ����� ���, ���������� null.
     * @param x - ���������� �����
     * @param y  - ���������� �����
     * @return ����, �������� ����������� ����� (x, y)
     */
    IUsualLayerSchema focusLayerSchema(int x, int y);

    /**
     * ���������� ����, �������� ����������� ����� (x, y).
	 * ���� ����� ����� ���������, ���������� ��������� � ������.
	 * ���� ����� ����� ���, ���������� null.
     * @param x - ���������� �����
     * @param y  - ���������� �����
     * @return ����, �������� ����������� ����� (x, y)
     */
    IUsualLayerSchema getLayerSchema(int x, int y);

    /**
     * @return ������ ���� ����� � ����
     */
    List<? extends IUsualLayerSchema> getLayersSchema();

    /**
     * ��������� ������ ������� � ���� �� source ���� � dest ����.
     * ���� ����� ������� ������� ������, �� ���������� null.
     * @param source - ����, �� �������� ������� �������
     * @param dest - ����, ���� ������ �������
     * @return �������
     */
    IConnectionSchema addDirectConnectionSchema(IUsualLayerSchema source, IUsualLayerSchema dest);

    /**
     * @return ���������� true ���� �� ���� source ����� �������� ������ �������
     * ����� false
     *  @param source ����, �� �������� ����� �������� ������ �������
     * */
    boolean canAddDirectConnectionSchema(IUsualLayerSchema source);

    /**
     * @return ���������� true ���� � ���� dest ����� �������� ������ �������
     * ����� false
     *  @param source - ����, �� �������� ����� �������� ������ �������
     *  @param dest - ����, � ������� ����� �������� ������ �������
     * */
    boolean canAddDirectConnectionSchema(IUsualLayerSchema source, IUsualLayerSchema dest);

    /**
     * @return ���������� true ���� �� ���� source ����� �������� �������� �������
     * ����� false
     * @param source - ����, �� �������� ����� �������� �������� �������
     * */
    boolean canAddBackConnectionSchema(IUsualLayerSchema source);

    /**
     * @return ���������� true ���� � ���� dest ����� �������� �������� �������
     * ����� false
     * @param source - ���� �� �������� ����� �������� �������� �������
     * @param dest - ���� � ������� ����� �������� �������� �������
     * */
    boolean canAddBackConnectionSchema(IUsualLayerSchema source, IUsualLayerSchema dest);

    /**
     * ��������� �������� ������� � ���� �� source ���� � dest ����.
     * ���� ����� ������� ������� ������, �� ���������� null.
     * @param source - ����, �� �������� ������� �������
     * @param dest - ����, ���� ������ �������
     * @return �������
     */
    IConnectionSchema addBackConnectionSchema(IUsualLayerSchema source, IUsualLayerSchema dest);

    /**
     * ������� ������� �� ����
     * @param connectionSchema - ��������� �������
     */
    void removeConnectionSchema(IConnectionSchema connectionSchema);

    /**
     * ��������� ���� � ���� � �������������� �������
     * */
    void topologicalSort();

    /**
     * ������� ������(���� ��� �������) �� ����
     * @param shapeSchema - ��������� ������
     */
    void removeShape(IShapeSchema shapeSchema);

    /**
     * ������� ������ ��������� ����
     * @return ������ ��������� ����
     * */
    INeuroNet createNeuroNet();

    /**
     * ���������� ������ ��������� ����
     * @return ������ ��������� ����
     * */
    INeuroNet getNeuroNet();

    /**
     * ������� ������ ��������� ����
     * */
    void deleteNeuroNet();
}
