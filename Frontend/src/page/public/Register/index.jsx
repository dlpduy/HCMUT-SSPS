import { useContext } from "react";
import { Form, Input, Button } from "antd";
import { useNavigate } from "react-router-dom";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { register } from "../../../api/auth";
import { MyContext } from "../../../config/context";
import logo from "../../../assets/img/logo1.png";
import printer from "../../../assets/img/main.png";
const Register = () => {
  const navigate = useNavigate();
  const { openNotification } = useContext(MyContext);

  const handleLogin = (values) => {
    register(values).then((res) => {
      if (!res.error) {
        openNotification("Tạo tài khoản thành công!", "success");
        navigate(`/login`);
      } else {
        openNotification(res.error, "error");
      }
    });
  };
  return (
    <div className="grid grid-cols-5 grid-rows-1 gap-4 w-full h-full">
      <div className="col-span-2 bg-gradient-to-tr from-lightblue to-darkblue flex flex-col">
        <img src={logo} alt="" className="pt-5 w-fit h-fit" />
        <div className="w-2/3 text-2xl font-bold text-white self-center text-center">Dịch vụ in ấn thông minh</div>
        <div className="text-2xl font-bold text-white self-center">Trường Đại học Bách Khoa</div>
        <div className="text-2xl font-bold text-white self-center">ĐHQG Hồ Chí Minh</div>
        <img src={printer} alt="" className="pt-5 w-fit h-fit self-end" />
      </div>
      <Form
        initialValues={{
          remember: true,
        }}
        onFinish={handleLogin}
        autoComplete="off"
        layout="vertical"
        requiredMark="optional"
        className="col-span-3 col-start-3 h-full content-center w-2/3 justify-self-center"
      >
        <div className="text-4xl font-bold text-lightblue text-center pb-5">Đăng ký tài khoản</div>
        <Form.Item
          name="fullName"
          rules={[
            {
              required: true,
              message: "Please input your Fullname!",
            },
          ]}
        >
          <Input prefix={<UserOutlined />} placeholder="Fullname" className="h-10" />
        </Form.Item>
        <Form.Item
          name="username"
          rules={[
            {
              required: true,
              message: "Please input your Username!",
            },
          ]}
        >
          <Input prefix={<UserOutlined />} placeholder="Username" className="h-10" />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[
            {
              required: true,
              message: "Please input your Password!",
            },
            {
              validator: (_, value) => {
                if (value != undefined && value != null) {
                  if (!/^.{8,16}$/.test(value)) {
                    return Promise.reject("Password must have 8-16 symbols!");
                  } else if (!/(?=.*[A-Z])/.test(value)) {
                    return Promise.reject("Password must have uppercase letter!");
                  } else if (!/(?=.*[a-z])/.test(value)) {
                    return Promise.reject("Password must have lowercase letter!");
                  } else if (!/(?=.*\d)/.test(value)) {
                    return Promise.reject("Password must have number!");
                  } else if (!/(?=.*[!@#$%^&*])/.test(value)) {
                    return Promise.reject("Password must have special symbol!");
                  }
                  return Promise.resolve();
                } else {
                  return Promise.reject();
                }
              },
            },
          ]}
        >
          <Input.Password prefix={<LockOutlined />} type="password" placeholder="Password" className="h-10" />
        </Form.Item>
        <Form.Item className="mb-0">
          <Button
            htmlType="submit"
            className="w-full h-10 text-lg bg-lightblue text-white font-extrabold font-sans hover:!border-0 hover:!bg-darkblue hover:!text-white"
          >
            Đăng ký
          </Button>
        </Form.Item>
        <div className="flex items-center justify-between w-full pt-5">
          <div className="text-gray-600">{`Already have an account?`}</div>
          <Button
            onClick={() => navigate("/login")}
            className="w-1/2 bg-transparent border-none shadow-none text-darkblue font-extrabold font-sans text-lg hover:!text-lightblue hover:!bg-transparent"
          >
            Đăng nhập
          </Button>
        </div>
      </Form>
    </div>
  );
};

export default Register;
