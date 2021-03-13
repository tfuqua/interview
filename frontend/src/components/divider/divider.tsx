import React from "react";

type DividerType = {
  variant?: "horizontal" | "vertical";
  color?: "neutral" | "accent" | "info" | "warning" | "success" | "error";
  size?: "smallest" | "small" | "medium" | "large";
  className?: string;
};

export const Divider = ({
  variant = "horizontal",
  color = "neutral",
  size = "medium",
  className = "",
}: DividerType) => {
  const colorClasses = {
    neutral: "bg-gray-200",
    accent: "bg-indigo-200",
    info: "bg-blue-200",
    warning: "bg-yellow-200",
    success: "bg-green-200",
    error: "bg-red-200",
  };
  const sizeClass =
    size === "smallest"
      ? "pl-0.5 pb-0.5"
      : size === "small"
      ? "p-1"
      : size === "medium"
      ? "p-2"
      : "p-4";
  const variantClass =
    variant === "horizontal" ? "w-full my-2" : "h-auto self-stretch mx-2";

  return (
    <div
      className={`${colorClasses[color]} ${variantClass} ${sizeClass} ${className}`}
    ></div>
  );
};
