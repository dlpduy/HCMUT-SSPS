import { useState, useEffect, useContext } from "react";
import { getFilesUploaded, uploadFiled } from "./../../../../../api/student";
import { Table, Spin } from "antd";
import { MyContext } from "./../../../../../config/context/index";

const ChooseDocument = ({ setDataSend, setIndex, index }) => {
  const { openNotification } = useContext(MyContext);

  const [fileList, setFileList] = useState([]);
  const [selectedFile, setSelectedFile] = useState(null);
  const [inProgress, setInProgress] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    getFilesUploaded().then((res) => {
      setFileList(res.data);
      setIsLoading(true);
    });
  }, []);

  // Định nghĩa cột cho bảng
  const columns = [
    {
      title: "Mã tìa liệu",
      dataIndex: "fileId",
      width: "10%",
    },
    {
      title: "Tên tài liệu",
      dataIndex: "fileName",
      width: "70%",
    },
    {
      title: "Loại tài liệu",
      dataIndex: "fileType",
      width: "20%",
    },
  ];

  const handleUpload = () => {
    setInProgress(true);
    const lastDotIndex = selectedFile.name.lastIndexOf(".");
    if (lastDotIndex === -1) {
      return { nameName: selectedFile.name, fileType: "" };
    }

    const fileName = selectedFile.name.substring(0, lastDotIndex);
    const fileType = selectedFile.name.substring(lastDotIndex);
    uploadFiled({ fileName, fileType }).then(() => openNotification("Upload file thành công!", "success"));
    setSelectedFile();
  };

  const handleFileChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  return (
    <div className="w-full h-full flex flex-col gap-5">
      <h2 className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Chọn tài liệu</h2>
      <div className="py-5 flex flex-row w-full">
        <label htmlFor="fileInput" className="cursor-pointer bg-darkblue !text-white py-2 px-4 rounded mr-5">
          Thêm file mới
        </label>
        <input id="fileInput" type="file" className="!hidden" onChange={handleFileChange} />
        {selectedFile && <p> {selectedFile.name}</p>}
        <button
          type="button"
          className="cursor-pointer bg-white !text-lightblue py-2 px-4 rounded disabled:cursor-not-allowed"
          disabled={selectedFile == null ? true : false}
          onClick={handleUpload}
        >
          {inProgress ? <Spin /> : "Upload"}
        </button>
      </div>
      <Table
        columns={columns}
        dataSource={fileList}
        pagination={{ pageSize: 10 }}
        loading={!isLoading}
        onRow={(record) => {
          return {
            onClick: () => {
              setDataSend((prevData) => ({ ...prevData, fileId: record.fileId }));
              setIndex(index + 1);
            },
          };
        }}
      />
    </div>
  );
};

export default ChooseDocument;
