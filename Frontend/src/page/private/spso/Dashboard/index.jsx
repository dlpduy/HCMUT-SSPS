import { useEffect, useState } from "react";
import { getStatistic } from "../../../../api/spso";
import { Statistic } from "antd";

const Dashboard = () => {
  const [statistic, setStatistic] = useState();

  useEffect(() => {
    getStatistic().then((res) => setStatistic(res.data));
    const interval = setInterval(() => {
      getStatistic().then((res) => setStatistic(res.data));
    }, 10 * 60 * 1000);

    return () => clearInterval(interval);
  }, []);

  return (
    <div className="flex justify-center items-center w-full h-full">
      <div className="grid grid-cols-2 grid-rows-1 gap-4 w-1/2 h-1/2 bg-white rounded-3xl border border-darkblue">
        <Statistic title="Tổng số dịch vụ in đã thực hiện" value={statistic?.total_printing} className="place-content-center place-self-center" />
        <Statistic title="Tổng số giao dịch đã thực hiện" value={statistic?.total_printing} className="place-content-center place-self-center" />
      </div>
    </div>
  );
};

export default Dashboard;
