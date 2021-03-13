import React from "react";
import InfoIcon from "@material-ui/icons/Info";
import SuccessIcon from "@material-ui/icons/Check";
import ErrorIcon from "@material-ui/icons/Error";
import WarningIcon from "@material-ui/icons/Warning";
import { Divider } from "../divider/divider";

type CalloutType = {
  color: "info" | "warning" | "error" | "success";
  children: React.ReactNode;
};

/**
 * Notice that we don't do interpolation of those colors into classNames, otherwise that will muck with tailwind purging!
 * @param param0
 * @returns
 */
export const Callout = ({ children, color }: CalloutType) => {
  const containerClassNames = {
    info: "border-blue-200 bg-blue-50",
    warning: "border-yellow-200 bg-yellow-50",
    error: "border-red-200 bg-red-50",
    success: "border-green-200 bg-green-50",
  };
  const iconClassNames = {
    info: "text-blue-400",
    warning: "text-yellow-400",
    error: "text-red-400",
    success: "text-green-400",
  };
  const IconToUse = {
    info: InfoIcon,
    warning: WarningIcon,
    error: ErrorIcon,
    success: SuccessIcon,
  };

  const Icon = IconToUse[color];
  return (
    <div
      className={`border-2 p-2 flex flex-row items-start ${containerClassNames[color]}`}
    >
      <Icon className={iconClassNames[color]} />
      <Divider variant="vertical" size="smallest" color={color} />
      <div>{children}</div>
    </div>
  );
};
