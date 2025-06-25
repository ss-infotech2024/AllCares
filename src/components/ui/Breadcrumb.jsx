import * as React from "react";
import { Slot } from "@radix-ui/react-slot";
import { ChevronRight, MoreHorizontal } from "lucide-react";

import { cn } from "@/lib/utils";

// Root <nav> element
const Breadcrumb = React.forwardRef((props, ref) => {
  return <nav ref={ref} aria-label="breadcrumb" {...props} />;
});
Breadcrumb.displayName = "Breadcrumb";

// Ordered list container
const BreadcrumbList = React.forwardRef((props, ref) => {
  const { className, ...rest } = props;
  return (
    <ol
      ref={ref}
      className={cn(
        "flex flex-wrap items-center gap-1.5 break-words text-sm text-muted-foreground sm:gap-2.5",
        className
      )}
      {...rest}
    />
  );
});
BreadcrumbList.displayName = "BreadcrumbList";

// Individual breadcrumb item
const BreadcrumbItem = React.forwardRef((props, ref) => {
  const { className, ...rest } = props;
  return (
    <li
      ref={ref}
      className={cn("inline-flex items-center gap-1.5", className)}
      {...rest}
    />
  );
});
BreadcrumbItem.displayName = "BreadcrumbItem";

// Link element in breadcrumb
const BreadcrumbLink = React.forwardRef((props, ref) => {
  const { asChild, className, ...rest } = props;
  const Comp = asChild ? Slot : "a";
  return (
    <Comp
      ref={ref}
      className={cn("transition-colors hover:text-foreground", className)}
      {...rest}
    />
  );
});
BreadcrumbLink.displayName = "BreadcrumbLink";

// Current page span
const BreadcrumbPage = React.forwardRef((props, ref) => {
  const { className, ...rest } = props;
  return (
    <span
      ref={ref}
      role="link"
      aria-disabled="true"
      aria-current="page"
      className={cn("font-normal text-foreground", className)}
      {...rest}
    />
  );
});
BreadcrumbPage.displayName = "BreadcrumbPage";

// Separator (e.g. > icon)
const BreadcrumbSeparator = ({ children, className, ...props }) => (
  <li
    role="presentation"
    aria-hidden="true"
    className={cn("[&>svg]:size-3.5", className)}
    {...props}
  >
    {children ?? <ChevronRight />}
  </li>
);
BreadcrumbSeparator.displayName = "BreadcrumbSeparator";

// Ellipsis component for overflow
const BreadcrumbEllipsis = ({ className, ...props }) => (
  <span
    role="presentation"
    aria-hidden="true"
    className={cn("flex h-9 w-9 items-center justify-center", className)}
    {...props}
  >
    <MoreHorizontal className="h-4 w-4" />
    <span className="sr-only">More</span>
  </span>
);
BreadcrumbEllipsis.displayName = "BreadcrumbEllipsis";

// Export components
export {
  Breadcrumb,
  BreadcrumbList,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbPage,
  BreadcrumbSeparator,
  BreadcrumbEllipsis,
};
