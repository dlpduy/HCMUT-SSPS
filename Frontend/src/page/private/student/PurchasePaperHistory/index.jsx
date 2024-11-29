import { useState, useEffect } from "react";
import { getPagePurchaseHistory } from "../../../../api/student";

import { Table } from "antd";

const PurchasePaperHistory = () => {
  const [purchaseList, setPurchaseList] = useState([]);

  useEffect(() => {
    getPagePurchaseHistory().then((res) => setPurchaseList(res.data));
  }, []);

  // Định nghĩa cột cho bảng
  const columns = [
    {
      title: "Mã giao dịch",
      dataIndex: "orderId",
      width: "10%",
    },
    {
      title: "Thông tin giao dịch",
      dataIndex: "papers",
      width: "20%",
      render: (values) => {
        return (
          <div className="flex flex-col">
            {values.map((value, index) => {
              return (
                <div className="flex flex-row gap-3" key={index}>
                  <div>Loại giấy: {value.paperType}</div>|<div>Số lượng: {value.quantity}</div>
                </div>
              );
            })}
          </div>
        );
      },
    },
    {
      title: "Ngày giao dịch",
      dataIndex: "time",
      render: (value) => {
        const date = new Date(value);
        return `${String(date.getHours())}:${String(date.getMinutes())} ${String(date.getDate())} - ${String(date.getMonth() + 1)} - ${date.getFullYear()}`;
      },
      width: "30%",
    },
    {
      title: "Tổng tiền",
      dataIndex: "totalPrice",
      width: "20%",
      render: (value) => String(value).replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " VND",
    },
  ];

  return (
    <div className="w-full h-full bg-white pt-5 px-5 flex flex-col gap-5">
      <h2 className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Lịch sử mua giấy in</h2>
      <Table columns={columns} dataSource={purchaseList} pagination={false} />
    </div>
  );
};

export default PurchasePaperHistory;
