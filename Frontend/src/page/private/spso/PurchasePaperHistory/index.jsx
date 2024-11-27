import { Table } from "antd";

const PurchasePaperHistory = () => {
  // Định nghĩa cột cho bảng
  const columns = [
    {
      title: "Người mua",
      dataIndex: "buyer",
      key: "buyer",
    },
    {
      title: "Loại giấy in",
      dataIndex: "paperType",
      key: "paperType",
    },
    {
      title: "Ngày mua",
      dataIndex: "buyDate",
      key: "butDate",
    },
    {
      title: "Số lượng",
      dataIndex: "quantity",
      key: "quantity",
    },
    {
      title: "Giá tiền",
      dataIndex: "price",
      key: "price",
    },
    {
      title: "Tổng tiền",
      dataIndex: "totalMoney",
      key: "totalMoney",
    },
  ];

  // Dữ liệu giả
  const data = [
    {
      key: "1", // Unique key cho mỗi dòng
      buyer: "Nguyễn Văn A",
      paperType: "A4",
      buyDate: "20-10-2024",
      quantity: 10,
      price: 2000,
      totalMoney: 20000, // quantity * price
    },
    {
      key: "2",
      buyer: "Trần Thị B",
      paperType: "A3",
      buyDate: "21-10-2024",
      quantity: 5,
      price: 3000,
      totalMoney: 15000,
    },
    {
      key: "3",
      buyer: "Lê Văn C",
      paperType: "Giấy ảnh",
      buyDate: "22-10-2024",
      quantity: 2,
      price: 10000,
      totalMoney: 20000,
    },
    {
      key: "4",
      buyer: "Hoàng Thị D",
      paperType: "A4",
      buyDate: "23-10-2024",
      quantity: 20,
      price: 2000,
      totalMoney: 40000,
    },
    {
      key: "5",
      buyer: "Phạm Văn E",
      paperType: "A4",
      buyDate: "24-10-2024",
      quantity: 15,
      price: 2000,
      totalMoney: 30000,
    },
  ];

  return (
    <div className="w-full h-full bg-white pt-5 px-5">
      <h2 className="w-1/2 border-b border-slate-400 mb-5 pb-3 text-2xl font-bold text-darkblue">Lịch sử mua giấy in</h2>
      <Table columns={columns} dataSource={data} />
    </div>
  );
};

export default PurchasePaperHistory;
