import { useContext } from "react";
import { Form, Input, Button } from "antd";
import { useNavigate } from "react-router-dom";
import { flushSync } from "react-dom";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import Cookies from "js-cookie";
import { login } from "../../../api/auth";
import { MyContext } from "../../../config/context";
import logo from "../../../assets/img/logo1.png";
import printer from "../../../assets/img/main.png";

const Login = () => {
  const navigate = useNavigate();
  const { setRole, openNotification } = useContext(MyContext);

  const handleLogin = (values) => {
    login(values).then((res) => {
      if (res.message == "Login successfully") {
        flushSync(() => {
          Cookies.set("name", res.data.user.fullName, {
            path: "/",
          });
          Cookies.set("role", res.data.user.role, {
            path: "/",
          });
          Cookies.set("token", res.data.accessToken, {
            path: "/",
          });
          setRole(res.data.user.role.toLowerCase());
        });
        openNotification(res.message, "success");
        navigate(`/main`);
      } else {
        openNotification(res.message, "error");
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
        <div className="text-4xl font-bold text-lightblue text-center pb-5">Đăng nhập tài khoản</div>
        <Form.Item
          name="username"
          rules={[
            {
              required: true,
              message: "Please input your Username!",
            },
            {
              validator: (_, value) => {
                if (value != undefined && value != null) {
                  if (!/^[a-zA-Z0-9._%+-]+@hcmut\.edu\.vn$/.test(value)) {
                    return Promise.reject("Nhập tài khoản HCMUT của bạn!");
                  }
                  return Promise.resolve();
                } else {
                  return Promise.reject();
                }
              },
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
        <div className="w-full h-fit flex flex-row items-center justify-between">
          <Form.Item className="mb-0 w-full">
            <Button
              htmlType="submit"
              className="w-full h-10 text-lg bg-lightblue text-white font-extrabold font-sans hover:!border-0 hover:!bg-darkblue hover:!text-white"
            >
              Đăng nhập
            </Button>
          </Form.Item>
          <Button className="border-none bg-transparent hover:!bg-transparent hover:!text-lightblue shadow-none">Forgot password?</Button>
        </div>
        <div className="flex items-center justify-between w-full pt-5">
          <div className="text-gray-600">{`Don't have an account?`}</div>
          <Button
            onClick={() => navigate("/register")}
            className="bg-transparent border-none shadow-none text-darkblue font-extrabold font-sans text-lg hover:!text-lightblue hover:!bg-transparent"
          >
            Đăng ký
          </Button>
        </div>
      </Form>
    </div>
  );
};

export default Login;
