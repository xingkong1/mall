package mall.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 静态资源压缩过滤器
 * @author xingkong
 *
 */

public class GzipFilter implements Filter {
	
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)res;
		BufferResponse bufferResponse=new BufferResponse(response);
		chain.doFilter(request, bufferResponse);
		byte[] buffer=bufferResponse.getByteBuffer();
		System.out.println("压缩前的大小为："+buffer.length);
		ByteArrayOutputStream bStream=new ByteArrayOutputStream();
		GZIPOutputStream gOutputStream=new GZIPOutputStream(bStream);
		gOutputStream.write(buffer);
		gOutputStream.close();
		byte[] zipBuffer=bStream.toByteArray();
		System.out.println("压缩后的大小为："+zipBuffer.length);
		response.setHeader("content-encoding", "gzip");
		response.setContentLength(zipBuffer.length);
		response.getOutputStream().write(zipBuffer);
	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}
	
	class BufferResponse extends HttpServletResponseWrapper{

		private HttpServletResponse response;
		private PrintWriter printWriter;
		private ByteArrayOutputStream bOutputStream=new ByteArrayOutputStream();
		
		public BufferResponse(HttpServletResponse response) {
			super(response);
			this.response=response;
			// TODO 自动生成的构造函数存根
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			// TODO 自动生成的方法存根
			return new MyServletOutputStream(bOutputStream);
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			printWriter=new PrintWriter(new OutputStreamWriter
					(bOutputStream, this.response.getCharacterEncoding()));
			return printWriter;
		}
		
		public byte[] getByteBuffer(){
			try {
				if(printWriter!=null){
					printWriter.close();
				}
				
				if(bOutputStream!=null){
					bOutputStream.flush();
					return bOutputStream.toByteArray();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		
	}
	
	
	
	class MyServletOutputStream extends ServletOutputStream{
		
		private ByteArrayOutputStream bOutputStream;
		
		public MyServletOutputStream(ByteArrayOutputStream bStream){
			this.bOutputStream=bStream;
		}

		@Override
		public void write(int b) throws IOException {
			this.bOutputStream.write(b);
		}

		@Override
		public boolean isReady() {
			// TODO 自动生成的方法存根
			return false;
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {
			// TODO 自动生成的方法存根
			
		}
		
	}

}
