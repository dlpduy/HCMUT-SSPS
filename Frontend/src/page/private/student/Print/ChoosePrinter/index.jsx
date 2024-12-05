import { useState, useEffect } from "react";
import { getAllPrinters } from "../../../../../api/shared";
import { Table, Pagination } from "antd";

const ChoosePrinter = ({ setDataSend, setOpen }) => {
  const [printerList, setPrinterList] = useState([]);
  const [page, setPage] = useState(0);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    getAllPrinters({ page: page }).then((res) => {
      setPrinterList(res.data);
      setIsLoading(true);
    });
  }, [page]);

  // Định nghĩa cột dữ liệu
  const columns = [
    {
      title: "ID",
      dataIndex: "id",
      width: "5%",
    },
    {
      title: "Tên thương hiệu",
      dataIndex: "brand",
      width: "10%",
    },
    {
      title: "Mẫu mã",
      dataIndex: "model",
      width: "15%",
    },
    {
      title: "Mô tả ngắn",
      dataIndex: "description",
      width: "40%",
      render: (text) => <span style={{ whiteSpace: "nowrap", textOverflow: "ellipsis", overflow: "hidden" }}>{text}</span>,
    },
    {
      title: "Cơ sở",
      dataIndex: "campusName",
      width: "10%",
    },
    {
      title: "Vị trí",
      dataIndex: "buildingName",
      width: "10%",
    },
    {
      title: "Phòng",
      dataIndex: "roomNum",
      width: "10%",
    },
  ];

  const handlePageChange = (e) => {
    setPage(e);
  };

  return (
    <div className="w-full h-full flex flex-col gap-5">
      <h2 className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Danh sách máy in</h2>
      <Table
        columns={columns}
        dataSource={printerList?.content}
        pagination={false}
        loading={!isLoading}
        onRow={(record) => {
          return {
            onClick: () => {
              setDataSend((prevData) => ({ ...prevData, printerId: record.id }));
              setOpen(true);
            },
          };
        }}
      />
      <Pagination
        defaultCurrent={1}
        total={printerList?.total || 0}
        current={page + 1}
        pageSize={printerList?.pageSize || 10}
        onChange={handlePageChange}
        className="self-end"
      />
    </div>
  );
};

export default ChoosePrinter;
