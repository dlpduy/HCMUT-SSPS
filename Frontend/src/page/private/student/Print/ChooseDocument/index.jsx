import { Table } from "antd";
import { Overlay } from "antd/es/popconfirm/PurePanel";

const ChooseDocument = () => {
  // Định nghĩa cột cho bảng
  const columns = [
    {
      title: 'Tên tài liệu',
      dataIndex: 'docName',
      key: 'docName',
    },
    {
      title: 'Chỉnh sửa lần cuối',
      dataIndex: 'lastEdited',
      key: 'lastEdited',
    },
    {
      title: 'Kích thước tệp',
      dataIndex: 'fileSize',
      key: 'fileSize',
    },
  ];

  // Dữ liệu giả
  const data = [
    {
      docName: 'Đề cương môn Toán Cao cấp',
      lastEdited: '25-10-2024',
      fileSize: '1.2 MB',
    },
    {
      docName: 'Tổng hợp lý thuyết môn Xác suất Thống kê',
      lastEdited: '15-10-2024',
      fileSize: '950 KB',
    },
    {
      docName: 'Đề thi mẫu môn Vật lý 1',
      lastEdited: '10-10-2024',
      fileSize: '1.8 MB',
    },
    {
      docName: 'Lý thuyết Thí nghiệm Vật lý',
      lastEdited: '05-10-2024',
      fileSize: '1.1 MB',
    },
    {
      docName: 'Đề cương ôn tập Đại số Tuyến tính',
      lastEdited: '2024-11-20',
      fileSize: '2.5 MB',
    },
  ];

  return (
    <div className="p-4 bg-lightblue min-h-screen">
      <h2 className="text-darkblue text-xl font-bold mb-4" style={{paddingTop: '150px', paddingLeft: '150px' }}>Chọn tài liệu</h2>
      <div style={{ display: 'grid', placeItems: 'center', paddingTop: '5px' }}>
        <Table
          columns={columns}
          dataSource={data}
          pagination={{ pageSize: 5 }}
          className="bg-white rounded shadow-lg"
          style={{ width: '80%' }}
        />
      </div>
    </div>
  );
};

export default ChooseDocument;
