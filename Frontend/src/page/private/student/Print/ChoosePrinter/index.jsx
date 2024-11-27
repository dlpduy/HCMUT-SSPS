
import { Table } from "antd";

// Định nghĩa cột dữ liệu
const columns = [
  {
    title: "ID",
    dataIndex: "id",
    key: "id",
    render: (text) => <strong>{text}</strong>,
  },
  {
    title: "Tên thương hiệu",
    dataIndex: "brand",
    key: "brand",
  },
  {
    title: "Mẫu mã",
    dataIndex: "model",
    key: "model",
  },
  {
    title: "Địa điểm lắp đặt",
    dataIndex: "location",
    key: "location",
  },
];

// Dữ liệu máy in mẫu
const data = [
  {
    key: "1",
    id: "P001",
    brand: "Canon",
    model: "PIXMA G2020",
    location: "Thư viện H1",
  },
  {
    key: "2",
    id: "P002",
    brand: "HP",
    model: "LaserJet Pro M404dn",
    location: "Phòng IT",
  },
  {
    key: "3",
    id: "P003",
    brand: "Epson",
    model: "EcoTank L3150",
    location: "Phòng giáo vụ",
  },
];

const ChoosePrinter = () => {
  return (
    <div>
      <h2>Danh sách máy in</h2>
      <Table columns={columns} dataSource={data} />
    </div>
  );
};

export default ChoosePrinter;
