import { useState, useEffect, useContext } from "react";
import { Form, Input, InputNumber, Button, Table } from "antd";
import { getAllFileTypes, getAllPaper } from "../../../../api/shared";
import { createNewPaper, addNewFileType } from "../../../../api/spso";
import { MyContext } from "../../../../config/context";

const ConfigSystem = () => {
  const [fileForm] = Form.useForm();
  const [paperForm] = Form.useForm();
  const { openNotification } = useContext(MyContext);

  const [fileTypeList, setFileTypeList] = useState([]);
  const [paperTypeList, setPaperTypeList] = useState([]);
  const [getFile, setGetFile] = useState(false);
  const [getPaper, setGetPaper] = useState(false);

  useEffect(() => {
    getAllFileTypes().then((res) => {
      setFileTypeList(res.data);
      setGetFile(true);
    });
  }, [getFile]);

  useEffect(() => {
    getAllPaper().then((res) => {
      setPaperTypeList(res.data);
      setGetPaper(true);
    });
  }, [getPaper]);

  const colum1 = [
    {
      title: "Loại giấy",
      dataIndex: "type",
    },
    {
      title: "Height",
      dataIndex: "height",
    },
    {
      title: "Width",
      dataIndex: "width",
    },
    {
      title: "Giá",
      dataIndex: "price",
    },
  ];

  const colum2 = [
    {
      title: "Loại tài liệu",
      dataIndex: "type",
    },
    {
      title: "Mô tả",
      dataIndex: "description",
    },
  ];

  const handleAddNewFileType = (values) => {
    addNewFileType(values).then((res) => {
      if (res.message == "Create file successfully") {
        openNotification("Thêm loại file mới thành công!", "success");
        setGetFile(false);
        fileForm.resetFields();
      } else openNotification("Error!", "error");
    });
  };

  const handleAddNewPaperType = (values) => {
    createNewPaper(values).then((res) => {
      if (res.message) {
        openNotification("Thêm loại giấy mới thành công!", "success");
        setGetPaper(false);
        paperForm.resetFields();
      } else openNotification("Error!", "error");
    });
  };

  return (
    <div className="grid grid-cols-2 grid-rows-1 gap-4 pt-5">
      <div className="flex flex-col bg-white p-5 rounded-e-lg">
        <div className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue mb-5">Thêm loại file được tải lên</div>
        <Form layout="vertical" form={fileForm} onFinish={handleAddNewFileType}>
          <Form.Item
            name="type"
            label="Type"
            rules={[
              {
                required: true,
                message: "Cần phải nhập loại tài liệu!",
              },
            ]}
          >
            <Input placeholder="Nhập loại tài liệu" />
          </Form.Item>
          <Form.Item
            name="description"
            label="Mô tả"
            rules={[
              {
                required: true,
                message: "Cần phải nhập mô tả!",
              },
            ]}
          >
            <Input placeholder="Nhập mô tả" />
          </Form.Item>
          <Form.Item>
            <Button htmlType="submit" className="w-full bg-darkblue text-white font-bold text-lg h-15">
              Thêm
            </Button>
          </Form.Item>
        </Form>
        <div className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue mb-5">Các loại file hiện tại</div>
        <Table dataSource={fileTypeList} columns={colum2} pagination={false} />
      </div>
      <div className="flex flex-col bg-white p-5 rounded-s-lg">
        <div className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue mb-5">Thông tin giấy</div>
        <Form layout="vertical" form={paperForm} onFinish={handleAddNewPaperType}>
          <Form.Item
            name="type"
            label="Type"
            rules={[
              {
                required: true,
                message: "Cần phải nhập loại giấy!",
              },
            ]}
          >
            <Input placeholder="Nhập loại giấy" />
          </Form.Item>
          <Form.Item
            name="height"
            label="Độ dài"
            rules={[
              {
                required: true,
                message: "Cần phải nhập độ dài!",
              },
            ]}
          >
            <InputNumber placeholder="Nhập độ dài" className="w-full" />
          </Form.Item>
          <Form.Item
            name="width"
            label="Độ rộng"
            rules={[
              {
                required: true,
                message: "Cần phải nhập độ rộng!",
              },
            ]}
          >
            <InputNumber placeholder="Nhập độ rộng" className="w-full" />
          </Form.Item>

          <Form.Item
            name="price"
            label="Giá tiền"
            rules={[
              {
                required: true,
                message: "Cần phải nhập giá tiền!",
              },
            ]}
          >
            <InputNumber placeholder="Nhập giá tiền" className="w-full" />
          </Form.Item>
          <Form.Item>
            <Button htmlType="submit" className="w-full bg-darkblue text-white font-bold text-lg h-15">
              Thêm
            </Button>
          </Form.Item>
        </Form>
        <div className="pb-3 text-2xl font-bold text-darkblue mb-5">Các loại giấy hiện tại</div>
        <Table dataSource={paperTypeList} columns={colum1} pagination={false} />
      </div>
    </div>
  );
};

export default ConfigSystem;
